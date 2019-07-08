package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.CardrecordBiz;
import com.shouzan.back.biz.CodeBiz;
import com.shouzan.back.entity.Cardrecord;
import com.shouzan.back.entity.operate.CodeRecord;
import com.shouzan.back.entity.operate.RechargeRecord;
import com.shouzan.back.util.CsvUtil;
import com.shouzan.back.util.DateUtil;
import com.shouzan.back.vo.SearchSatisfy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: com.shouzan.back.rest.CardrecordExport
 * @Author: bin.yang
 * @Date: 2019/5/31 15:37
 * @Description: TODO  卡卷记录导出
 */
@Controller
@RequestMapping("recordExport")
@Validated
@Slf4j
public class CardRecordExport {

    private static final String[] STATUS = {"未发放","未核销","已核销","已过期","已退款"};
    private static final String[] STATUSP = {"充值中","充值成功","充值失败","已退款"};
    private static final String[] STATUSC = {"未发放","已发放"};
    private static final String[] TITLE = {"商户号卡卷记录","话费充值记录","运营H5卡卷记录","串码卡卷记录","免支付卡卷记录"};
    @Autowired
    private CardrecordBiz cardrecordBiz;
    @Autowired
    private CodeBiz codeBiz;
    @Value("${private_path}")
    private String PATH;

    /**
     * @Description: 卡卷记录导出csv
     * @param search
     * @param response
     * @[param] [search, response]
     * @return void
     * @author:  bin.yang
     * @date:  2019/5/31 4:05 PM
     */
    @RequestMapping(value = "/exportCsv", method = RequestMethod.POST)
    public void exportCsv(SearchSatisfy search, HttpServletResponse response) throws IOException {

        // 标题
        LinkedHashMap headers = new LinkedHashMap();
        headers.put("1", "交易记录类型");
        headers.put("2", "商品名称");
        headers.put("3", "用户手机号");
        headers.put("4", "创建时间");
        headers.put("5", "卡卷记录状态");
        headers.put("6", "消耗商户号");

        // 临时文件名称
        String temp = "TempFile"+ DateUtil.getMillisecond();

        // 导出文件名称
        String fileName = TITLE[search.getTypes()]+DateUtil.getDate()+".csv";

        // 线上服务器 临时文件 地址
        String path = PATH + "/";

        // 生成临时文件
        File csvFile = createCSVFile(search, headers, path, temp);

        // 将临时文件下载到客户端
        CsvUtil.exportFile(response,path+csvFile.getName(),fileName);

        // 删除临时文件
        CsvUtil.deleteFile(path,csvFile.getName());
    }

    /**
     * @Description: 生成csv文件
     * @param search
     * @param headers
     * @param outPutPath
     * @param fileName
     * @[param] [search, headers, outPutPath, fileName]
     * @return java.io.File
     * @author:  bin.yang
     * @date:  2019/5/31 4:06 PM
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

    private  List readPageList(SearchSatisfy search, int index) {

        if(search.getTypes() == 0){
            return readRecordPageList(search,index);
        }else if(search.getTypes() == 2){
            return readOperatePageList(search,index);
        }else if(search.getTypes() == 4){
            return readNoPayPageList(search,index);
        }else if(search.getTypes() == 1){
            return readTelephonePageList(search,index);
        }else if(search.getTypes() == 3){
            return readCodePageList(search,index);
        }else{
            return null;
        }
    }

    /**
     * @Description: 商户号卡卷记录
     * @param search
     * @param index
     * @[param] [search, index]
     * @return java.util.List
     * @author:  bin.yang
     * @date:  2019/5/31 5:25 PM
     */
    private List readRecordPageList(SearchSatisfy search, int index){
        List exportData = new ArrayList<Map>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PageHelper.startPage(index,500);
        List<Cardrecord> list = cardrecordBiz.queryPageList(search);
        if(list != null && list.size() > 0){
            Map row;  int a = 1;
            for (Cardrecord cardrecord : list) {
                row= new LinkedHashMap<String, String>();
                row.put("1", "商户号卡卷记录");
                row.put("2", cardrecord.getGoodsName());
                row.put("3", "`"+cardrecord.getUserTel());
                row.put("4", sim.format(cardrecord.getCreateTime()));
                row.put("5", STATUS[cardrecord.getCardState()]);
                row.put("6", cardrecord.getCconsumeMer() == null ? "":cardrecord.getCconsumeMer());
                exportData.add(row);
            }
        }
        return exportData;
    }

    /**
     * @Description: 运营H5卡卷记录
     * @param search
     * @param index
     * @[param] [search, index]
     * @return java.util.List
     * @author:  bin.yang
     * @date:  2019/5/31 5:37 PM
     */
    private List readOperatePageList(SearchSatisfy search, int index){
        List exportData = new ArrayList<Map>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PageHelper.startPage(index,500);
        List<Cardrecord> list = cardrecordBiz.queryPageList(search);
        if(list != null && list.size() > 0){
            Map row;  int a = 1;
            for (Cardrecord cardrecord : list) {
                row= new LinkedHashMap<String, String>();
                row.put("1", "H5卡卷记录");
                row.put("2", cardrecord.getGoodsName());
                row.put("3", "`"+cardrecord.getUserTel());
                row.put("4", sim.format(cardrecord.getCreateTime()));
                row.put("5", STATUS[cardrecord.getCardState()]);
                row.put("6", cardrecord.getCconsumeMer() == null ? "":cardrecord.getCconsumeMer());
                exportData.add(row);
            }
        }
        return exportData;
    }

    /**
     * @Description: 免支付卡卷记录
     * @param search
     * @param index
     * @[param] [search, index]
     * @return java.util.List
     * @author:  bin.yang
     * @date:  2019/5/31 5:37 PM
     */
    private List readNoPayPageList(SearchSatisfy search, int index){
        List exportData = new ArrayList<Map>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PageHelper.startPage(index,500);
        List<Cardrecord> list = cardrecordBiz.queryPageList(search);
        if(list != null && list.size() > 0){
            Map row;  int a = 1;
            for (Cardrecord cardrecord : list) {
                row= new LinkedHashMap<String, String>();
                row.put("1", "免支付卡卷记录");
                row.put("2", cardrecord.getGoodsNameo());
                row.put("3", "`"+cardrecord.getUserTel());
                row.put("4", sim.format(cardrecord.getCreateTime()));
                row.put("5", STATUS[cardrecord.getCardState()]);
                row.put("6", cardrecord.getCconsumeMer() == null ? "":cardrecord.getCconsumeMer());
                exportData.add(row);
            }
        }
        return exportData;
    }

    /**
     * @Description: 话费充值记录
     * @param search
     * @param index
     * @[param] [search, index]
     * @return java.util.List
     * @author:  bin.yang
     * @date:  2019/5/31 5:37 PM
     */
    private List readTelephonePageList(SearchSatisfy search, int index){
        List exportData = new ArrayList<Map>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PageHelper.startPage(index,500);
        List<RechargeRecord> list = cardrecordBiz.queryPageRechList(search);
        if(list != null && list.size() > 0){
            Map row;  int a = 1;
            for (RechargeRecord rechargeRecord : list) {
                row= new LinkedHashMap<String, String>();
                row.put("1", "话费充值记录");
                row.put("2", rechargeRecord.getGoodsName());
                row.put("3", "`"+rechargeRecord.getRechargeTel());
                row.put("4", sim.format(rechargeRecord.getCreateTime()));
                row.put("5", STATUSP[rechargeRecord.getState()]);
                row.put("6", "");
                exportData.add(row);
            }
        }
        return exportData;
    }

    /**
     * @Description: 串码卡卷记录
     * @param search
     * @param index
     * @[param] [search, index]
     * @return java.util.List
     * @author:  bin.yang
     * @date:  2019/5/31 5:41 PM
     */
    private List readCodePageList(SearchSatisfy search, int index){
        List exportData = new ArrayList<Map>();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PageHelper.startPage(index,500);
        List<CodeRecord> list = codeBiz.queryRecordPageList(search);
        if(list != null && list.size() > 0){
            Map row;  int a = 1;
            for (CodeRecord codeRecord : list) {
                row= new LinkedHashMap<String, String>();
                row.put("1", "串码卡卷记录");
                row.put("2", codeRecord.getGoodsNames());
                row.put("3", "`"+codeRecord.getUserTel());
                row.put("4", sim.format(codeRecord.getCreateTime()));
                row.put("5", STATUSC[codeRecord.getCardState()]);
                row.put("6", "");
                exportData.add(row);
            }
        }
        return exportData;
    }

}
