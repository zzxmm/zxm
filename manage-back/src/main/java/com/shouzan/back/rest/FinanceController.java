package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.FinanceBiz;
import com.shouzan.back.biz.impl.FinanceBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.constant.thread.SingleThreadPoolExecutor;
import com.shouzan.back.entity.ConsumeRecord;
import com.shouzan.back.entity.FinalAccount;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author: bin.yang
 * @Date: 2018/12/6 12:18
 * @Description:  对账单管理
 */
@Controller
@RequestMapping("/finalRecord")
@Validated
@Slf4j
public class FinanceController extends BaseController<FinanceBizImpl,ConsumeRecord> {

    @Autowired
    private FinanceBiz financeBiz;

    @Autowired
    private SystemUserMapper systemUserMapper;

    private static final CellProcessor[] userProcessorsSin = new CellProcessor[]{
            new ParseInt(),
            new ParseLong(),
            null,
            new ParseBigDecimal(),
            new ParseBigDecimal(),
            null,
            new StrReplace("`", ""),
            new ParseDate("yyyy-MM-dd HH:mm:ss"),
            new ParseInt(),
            null,
            new Optional(new StrReplace("`", "")),
    };
    private static final CellProcessor[] ProcessorsAll = new CellProcessor[]{
            new ParseInt(),
            new ParseLong(),
            null,
            new ParseBigDecimal(),
            new ParseBigDecimal(),
            null,
            new StrReplace("`", ""),
            new ParseDate("yyyy-MM-dd HH:mm:ss"),
            new ParseInt(),
            null,
            null,
            new Optional(new StrReplace("`", ""))
    };

    private final String[] headerAll = new String[]{"cbatchId", "cdiscountId", "cdiscountType", "cdiscountMoney", "corderMoney", "ctransactionType", "cpaymentNumber", "cconsumeTime", "cconsumeMer", "cinformation", "cequipmentNumber", "cbankSerialnumber"};

    private final String[] headerSin = new String[]{"cbatchId", "cdiscountId", "cdiscountType", "cdiscountMoney", "corderMoney", "ctransactionType", "cpaymentNumber", "cconsumeTime", "cconsumeMer", "cequipmentNumber", "cbankSerialnumber"};

    /**
     * @Description: (对账单分页列表查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.FinalAccount>
     * @author:  bin.yang
     * @date:  2018/12/18 12:48 PM
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<FinalAccount> page(@RequestParam(defaultValue = "50") int pageSize, @RequestParam(defaultValue = "1") int current,
                                                 SearchSatisfy search) {
        try {
            int count = financeBiz.queryPageCount(search);
            PageHelper.startPage(current, pageSize,false);
            List<FinalAccount> list = financeBiz.queryPageList(search);
            log.info("request : 对账单列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]", pageSize, current, search);
            return new ObjectRestResponse<FinalAccount>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("对账单分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (csv文件导入)
     * @param upfile
     * @[param] [upfile]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2018/12/18 12:48 PM
     */
    @RequestMapping(value = "/importCsv", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse importCsv(MultipartFile upfile) throws Exception {
        long stareTime = System.currentTimeMillis();
        InputStream in = upfile.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(in, "GBK");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String s = bufferedReader.readLine();
        String[] split = s.split(",");
        String[] header = headerSin;
        CellProcessor[] processor = userProcessorsSin;
        if (split.length == 12) {
            header = headerAll;
            processor = ProcessorsAll;
        }
        String currentUserId = getCurrentUserId();
        int userId = systemUserMapper.findIdByAccount(currentUserId);
        CsvBeanReader reader = new CsvBeanReader(bufferedReader, CsvPreference.STANDARD_PREFERENCE);
        ExecutorService executorService = null;
        try {
            //创建线程池
            List<Future<String>> futureList = new ArrayList<Future<String>>();
            int processors = Runtime.getRuntime().availableProcessors();

            // 获取单例线程池
            executorService = SingleThreadPoolExecutor.getFixedThreadPool(processors * 2);

            //分页读取数据后,加入线程池处理
            while (readCsvPageList(executorService, futureList, reader, header, processor, userId)) {
            }
            //获取线程处理结果
            int threadNum = 0;
            int Total = 0;
            for (Future<String> future : futureList) {
                while (true) {
                    if (future.isDone() && !future.isCancelled()) {
                        threadNum++;
                        Total += Integer.parseInt(future.get());
                        break;
                    } else {
                        Thread.sleep(1000);
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            log.info("数据更新完成 >> : 使用线线程数 " + threadNum + " 个 , 更新数据 " + Total + " 条 . 共耗时--: " + (endTime - stareTime) + "ms!");
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("数据更新完成 >> :  更新数据 " + Total + " 条 . 共耗时--: " + (endTime - stareTime) + "ms!");
        } finally {
            reader.close();
        }
    }

    /**
     * @param executorService
     * @param futureList
     * @param inFile
     * @param header
     * @param processor
     * @param userId
     * @return boolean
     * @Description: (解析csv读取数据)
     * @[param] [executorService, futureList, inFile, header, processor]
     * @author: bin.yang
     * @date: 2018/12/11 11:30 AM
     */
    private boolean readCsvPageList(ExecutorService executorService, List<Future<String>> futureList, CsvBeanReader inFile, String[] header, CellProcessor[] processor, int userId) throws IOException {
        int index = 0;
        boolean status = false;
        List<ConsumeRecord> content = new ArrayList<ConsumeRecord>();
        ConsumeRecord read;
        while ((read = inFile.read(ConsumeRecord.class, header, processor)) != null) {// 这里从第一行开始取数据
            content.add(read);
            index++;
            if (index == 500) {
                status = true;
                break;
            }
        }
        //添加到线程集合
        if (!content.isEmpty()) {
            Future<String> future = executorService.submit(threadUpdateDbJob(futureList.size(), content, userId));
            futureList.add(future);
        }
        return status;
    }

    /**
     * @param threadNo
     * @param read
     * @param userId
     * @return java.util.concurrent.Callable<java.lang.String>
     * @Description: (二次调用)
     * @[param] [threadNo, read]
     * @author: bin.yang
     * @date: 2018/12/11 11:31 AM
     */
    private Callable<String> threadUpdateDbJob(int threadNo, List<ConsumeRecord> read, int userId) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                int count = read.size();
                // 判断数据内容 调用具体执行
                if (read.get(0).getCdiscountType().indexOf("定额立减") >= 0) {
                    batchPageInsertDataOne(threadNo, read, userId);
                } else {
                    batchPageInsertDataTwo(threadNo, read, userId);
                }
                return String.valueOf(count);
            }
        };
    }

    /**
     * @param threadNo
     * @param read
     * @param userId
     * @return void
     * @Description: (购买记录批量插入)
     * @[param] [threadNo, read]
     * @author: bin.yang
     * @date: 2018/12/11 10:51 AM
     */
    private void batchPageInsertDataOne(int threadNo, List<ConsumeRecord> read, int userId) {
        long stime = System.currentTimeMillis();
        try {
            Thread.sleep(50);
            financeBiz.addPurchaseRecordList(read, userId);
            long etime = System.currentTimeMillis();
            log.info("线程" + threadNo + "批量事务插入：" + read.size() + " 个 , 总共耗时-----------------------:" + (etime - stime) + "ms!");
        } catch (Exception e) {
            log.info("批量执行插入异常:>>" + read.size());
            throw new RuntimeException();
        }
    }

    /**
     * @param threadNo
     * @param read
     * @param userId
     * @return void
     * @Description: (使用记录批量插入)
     * @[param] [threadNo, read]
     * @author: bin.yang
     * @date: 2018/12/11 10:51 AM
     */
    private void batchPageInsertDataTwo(int threadNo, List<ConsumeRecord> read, int userId) {
        long stime = System.currentTimeMillis();
        try {
            Thread.sleep(50);
            financeBiz.addConsumeRecordList(read, userId);
            long etime = System.currentTimeMillis();
            log.info("线程" + threadNo + "批量事务插入：" + read.size() + " 个 , 总共耗时-----------------------:" + (etime - stime) + "ms!");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("批量执行插入异常:>>" + read.size());
            throw new RuntimeException();
        }
    }

    /**
     * @Description: (对账单 导出)
     * @param search
     * @param response
     * @[param] [search, response]
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/14 2:51 PM
     */
    @RequestMapping(value = "/exportCsv", method = RequestMethod.POST)
    public void exportCsv(SearchSatisfy search, HttpServletResponse response) throws IOException {
        LinkedHashMap headers = new LinkedHashMap();
        headers.put("1", "购买批次ID");
        headers.put("2", "购买优惠ID");
        headers.put("3", "购买优惠类型");
        headers.put("4", "购买优惠金额（元）");
        headers.put("5", "购买订单总金额（元）");
        headers.put("6", "购买交易类型");
        headers.put("7", "购买支付单号");
        headers.put("8", "购买消耗时间");
        headers.put("9", "购买消耗商户号");
        headers.put("10", "购买设备号");
        headers.put("11", "购买银行流水号");
        headers.put("12", "使用批次ID");
        headers.put("13", "使用优惠ID");
        headers.put("14", "使用优惠类型");
        headers.put("15", "使用优惠金额（元）");
        headers.put("16", "使用订单总金额（元）");
        headers.put("17", "使用交易类型");
        headers.put("18", "使用支付单号");
        headers.put("19", "使用消耗时间");
        headers.put("20", "使用消耗商户号");
        headers.put("21", "使用单品信息");
        headers.put("22", "使用设备号");
        headers.put("23", "使用银行流水号");
        headers.put("24", "卡卷记录状态");
        String temp = "tempFile";
        String fileName = "最终记录对账单.csv";
        // 线上服务器 临时文件 地址
        String path = CodeValid.PATH + "/";
        // 线下 临时文件地址
//        String path1 = "/Users/bin.yang/";
        // 生成临时文件
        File csvFile = createCSVFile(search, headers, path, temp);
        // 将临时文件下载到客户端
        exportFile(response,path+csvFile.getName(),fileName);
        // 删除临时文件
        deleteFile(path,csvFile.getName());
    }

    /**
     * @param search
     * @param headers
     * @param outPutPath
     * @param fileName
     * @return java.io.File
     * @Description: (写入临时文件)
     * @[param] [exportData, map, outPutPath, fileName]
     * @author: bin.yang
     * @date: 2018/12/14 1:22 PM
     */
    private File createCSVFile(SearchSatisfy search, LinkedHashMap headers, String outPutPath,
                               String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            log.info("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 8192);
            log.info("csvFileOutputStream：" + csvFileOutputStream);
            // 写入文件头部
            for (Iterator propertyIterator = headers.entrySet().iterator(); propertyIterator.hasNext(); ) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                csvFileOutputStream
                        .write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "" + "");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();

            List exportData ;
            int index = 1;
            while ((exportData = readPageList(search , index)) != null && exportData.size() > 0) {
                // 写入文件内容
                int b = 1 ;
                for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                    Object row = (Object) iterator.next();
                    for (Iterator propertyIterator = headers.entrySet().iterator(); propertyIterator
                            .hasNext(); ) {
                        Map.Entry propertyEntry = (Map.Entry) propertyIterator
                                .next();
                        csvFileOutputStream.write((String) BeanUtils.getProperty(row, (String) propertyEntry.getKey()));
                        if (propertyIterator.hasNext()) {
                            csvFileOutputStream.write(",");
                        }
                    }
                    if (iterator.hasNext()) {
                        csvFileOutputStream.newLine();
                    }
                }
                index++;
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                log.error("异常信息 : [{}]" , e);
            }
        }
        return csvFile;
    }

    /**
     * @Description: (数据库读取数据)
     * @param search
     * @param index
     * @[param] [search, index]
     * @return java.util.List
     * @author:  bin.yang
     * @date:  2018/12/18 12:47 PM
     */
    private  List readPageList(SearchSatisfy search, int index) {
        List exportData = new ArrayList<Map>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PageHelper.startPage(index,500);
        List<FinalAccount> list = financeBiz.queryPageList(search);
        if(list != null && list.size() > 0){
            Map row;  int a = 1;
            for (FinalAccount finalAccount : list) {
                    row= new LinkedHashMap<String, String>();
                    if(finalAccount.getPbatchId() != null){
                        row.put("1", finalAccount.getPbatchId());
                        row.put("2", finalAccount.getPdiscountId());
                        row.put("3", finalAccount.getPdiscountType());
                        row.put("4", finalAccount.getPdiscountMoney());
                        row.put("5", finalAccount.getPorderMoney());
                        row.put("6", finalAccount.getPtransactionType());
                        row.put("7", "`"+finalAccount.getPpaymentNumber());
                        row.put("8", sim.format(finalAccount.getPconsumeTime()));
                        row.put("9", finalAccount.getPconsumeMer());
                        row.put("10", finalAccount.getCequipmentNumber() != null ? finalAccount.getCequipmentNumber():"");
                        row.put("11", "`"+finalAccount.getPbankSerialnumber());
                    }else {
                        row.put("1", "");
                        row.put("2", "");
                        row.put("3", "");
                        row.put("4", "");
                        row.put("5", "");
                        row.put("6", "");
                        row.put("7", "");
                        row.put("8", "");
                        row.put("9", "");
                        row.put("10", "");
                        row.put("11", "");
                    }
                    if(finalAccount.getCbatchId() != null){
                        row.put("12", finalAccount.getCbatchId());
                        row.put("13", finalAccount.getCdiscountId());
                        row.put("14", finalAccount.getCdiscountType());
                        row.put("15", finalAccount.getCdiscountMoney());
                        row.put("16", finalAccount.getCorderMoney());
                        row.put("17", finalAccount.getCtransactionType());
                        row.put("18", "`"+finalAccount.getCpaymentNumber());
                        row.put("19", sim.format(finalAccount.getCconsumeTime()));
                        row.put("20", finalAccount.getCconsumeMer());
                        row.put("21", finalAccount.getCinformation() != null ? finalAccount.getCinformation().replace(",","，"):"");
                        row.put("22", finalAccount.getCequipmentNumber() != null ? finalAccount.getCequipmentNumber():"");
                        row.put("23", finalAccount.getCbankSerialnumber() != null ? "`"+finalAccount.getCbankSerialnumber():"");
                    }else {
                        row.put("12", "");
                        row.put("13", "");
                        row.put("14", "");
                        row.put("15", "");
                        row.put("16", "");
                        row.put("17", "");
                        row.put("18", "");
                        row.put("19", "");
                        row.put("20", "");
                        row.put("21", "");
                        row.put("22", "");
                        row.put("23", "");
                    }

                    row.put("24", finalAccount.getCardState() != null ? finalAccount.getCardState() : "");
                    exportData.add(row);
            }
        }
        return exportData;
    }

    /**
     * @Description: (下载到客户端)
     * @param response
     * @param csvFilePath
     * @param fileName
     * @[param] [response, csvFilePath, fileName]
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/14 2:50 PM
     */
    private  void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
            throws IOException {
        response.setContentType("application/csv;text/html=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        InputStream in = null;

        try {
            in = new FileInputStream(csvFilePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            response.setCharacterEncoding("UTF-8");
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //utf-8的文件在头部有三个字段是用来标识该文件的格式的
//                out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });//输出那三个字节的标示信息
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            log.error("异常信息 : [{}]" , e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("异常信息 : [{}]" , e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @Description: (删除临时文件)
     * @param filePath
     * @param fileName
     * @[param] [filePath, fileName]
     * @return void
     * @author:  bin.yang
     * @date:  2018/12/14 2:52 PM
     */
    private  void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(fileName)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        }
    }

}