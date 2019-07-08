package com.shouzan.back.rest;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.CodeBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.constant.thread.SingleThreadPoolExecutor;
import com.shouzan.back.entity.operate.CodeBase;
import com.shouzan.back.entity.operate.CodeList;
import com.shouzan.back.mapper.operate.CodeListMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @ClassName: com.shouzan.back.rest.CodeBaseController
 * @Author: bin.yang
 * @Date: 2019/4/11 15:04
 * @Description: TODO
 */
@Controller
@RequestMapping("/code")
@Validated
@Slf4j
public class CodeBaseController {

    @Autowired
    private CodeBiz codeBiz;

    @Autowired
    private CodeListMapper codeMapper;

    @Autowired
    private SystemUserController sysUserController;

    @Autowired
    private CodeAsynTask codeAsynTask;

    // 批次导入处理数据条数
    private static final int PAGE = 2000;

    /**
     * @Description: (串码库分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:50 PM
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            String id = codeBiz.findInIdByOperate(search);
            search.setBelonged(id);
            int count = codeBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<CodeBase> list = codeBiz.queryPageList(search);
            log.info("request : 串码库活动列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("串码库分页查询异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("获取信息失败 , 请刷新页面再次尝试 .");
        }
    }

    /**
     * @Description: (添加串码库信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:50 PM
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse add(@Valid CodeBase entity){
        try {
            Integer userId = sysUserController.getUserId();
            entity.setCreatorId(userId);
            log.info("request : 创建串码库 , 串码库信息[{}] ",entity);
            return codeBiz.createCodeBase(entity);
        } catch (Exception e) {
            log.error("添加串码库信息异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("创建失败,请重新创建 !");
        }
    }

    /**
     * @Description: (修改串码库信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:51 PM
     */
    @RequestMapping(value = "/updateInfo",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateInfo(@Valid CodeBase entity){
        try {
            Integer userId = sysUserController.getUserId();
            entity.setLastEditId(userId);
            log.info("request : 修改串码库信息 , 串码库信息[{}] ",entity);
            return codeBiz.updateCodeBase(entity);
        } catch (Exception e) {
            log.error("修改串码库信息异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改失败,请重新修改 !");
        }
    }

    /**
     * @Description: (删除串码库信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:51 PM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse deleteInfo(@NotNull(message = "请选择要删除的数据") Integer id){
        try {
            Integer userId = sysUserController.getUserId();
            log.info("request : 删除串码库信息 , 串码库ID[{}] , 操作人[{}] ",id,userId);
            return codeBiz.deleteCodeBase(id);
        } catch (Exception e) {
            log.error("删除串码库信息异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("删除失败,请重新操作 !");
        }
    }

    /**
     * @Description: (串码导入)
     * @param upFile
     * @param id
     * @[param] [upFile, id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/15 12:54 PM
     */
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse importExcel(@RequestParam("upFile") MultipartFile upFile,
                                          @NotNull(message = "串码库ID不能为NULL") Integer id) throws Exception {
        long s = System.currentTimeMillis();

        Integer maxId = codeMapper.findMaxIdByBaseId(id);

        //获取文件输入流
        InputStream in = upFile.getInputStream();

        //获取操作用户
        Integer userId = sysUserController.getUserId();

        /** 大数量 造成OOM 内存溢出 */
        //获取sheet页
        /*Sheet sheet = null;
        try {
            sheet = ExcelUtil.getExcelSheet(in);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ObjectRestResponse().rel(Response.FAILURE).msg("文件读取失败 , 请检查文件格式");
        }*/

        //多线程批量处理
        ExecutorService executorService = null;
        try {

            //创建线程池
            List<Future<String>> futures = new ArrayList<>();
            int processors = Runtime.getRuntime().availableProcessors();

            // 获取单例线程池
            executorService = SingleThreadPoolExecutor.getFixedThreadPool(processors * 2);

            //批量处理 , 存入mysql
            readListExcel(executorService,futures,in,userId,id);

            //获取线程执行结果
            int thread = 0;
            int total = 0;
            for (Future<String> future : futures) {
                while (true) {
                    if (future.isDone() && !future.isCancelled()) {
                        thread++;
                        total += Integer.parseInt(future.get());
                        break;
                    } else {
                        Thread.sleep(200);
                    }
                }
            }

            // 异步同步库存数量
            codeAsynTask.asynInsertStock(id,maxId);

            //输出执行结果
            long e = System.currentTimeMillis();
            log.info("串码数据导入完成 >> : 使用线线程数 " + thread + " 个 , 导入数据 " + total + " 条 . 共耗时--: " + (e - s)/1000 + "ms!");
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("串码数据导入完成 >> : 导入数据 " + total + " 条 . 共耗时--: " + (e - s)/1000 + "m!");
        } catch (Exception e) {
            executorService.shutdown();
            log.error("串码库数据导入异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("串码数据录入失败 , 请重新录入 ! ");
        }
    }

    /**
     * @Description: (分页读取添加到线程池中去执行)
     * @param executorService
     * @param futures
     * @param sheet
     * @param userId
     * @param baseId
     * @param index
     * @[param] [executorService, futures, sheet, userId, baseId, index]
     * @return boolean
     * @author:  bin.yang
     * @date:  2019/4/15 12:20 PM
     */
    /**
     * @Author: bin.yang
     * @Date: 2019/4/29 2:37 PM
     *
     * @Description: 方法弃用 大数据两OOM 内存溢出
     */
    /*private boolean readListExcel(ExecutorService executorService, List<Future<String>> futures, Sheet sheet, Integer userId, int baseId, int index) {

        // 每页读取条数
        int page = PAGE;
        //开始位置
        int start = (index * page) + 1;
        //计数
        int count = 0;
        boolean status = false;

        // 获取总条数
        int rows = sheet.getPhysicalNumberOfRows();
        List<CodeList> list = new ArrayList<>();
        CodeList codeList = new CodeList();

        //遍历sheet页
        for (int i = start; i < rows; i++) {
            Row row = sheet.getRow(i);
            if (row == null){
                continue;
            }
            Cell cell = row.getCell(0);
            String code;
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                DecimalFormat df = new DecimalFormat("0");
                code = df.format(cell.getNumericCellValue());
            }else {
                code = cell.getStringCellValue();
            }
            codeList.setBaseId(baseId);
            codeList.setCode(code);
            codeList.setCreatorId(userId);
            list.add(codeList);
            count++;
            if(count == page){
                status = true;
                break;
            }
        }

        // 添加到线程池中执行
        if(!list.isEmpty()){
            Future<String> future = executorService.submit(threadUpListDB(futures.size(), list));
            futures.add(future);
        }
        return status;
    }*/

    /**
     * @Description: 批次处理数据
     * @param executorService
     * @param futures
     * @param in
     * @param userId
     * @param baseId
     * @[param] [executorService, futures, in, userId, baseId]
     * @return void
     * @author:  bin.yang
     * @date:  2019/4/29 2:37 PM
     */
    private void readListExcel(ExecutorService executorService, List<Future<String>> futures, InputStream in, Integer userId, int baseId) {
        try {
            ExcelReader reader = new ExcelReader(in, ExcelTypeEnum.XLSX, null,
                    new AnalysisEventListener<List<String>>() {

                        List<CodeList> list = new ArrayList<CodeList>();

                        CodeList codeList ;

                        // 每次读取条数
                        int page = PAGE;

                        int count = 0;

                        @Override
                        public void invoke(List<String> object, AnalysisContext context) {
                            if(object.get(0) != null && context.getCurrentRowNum() != 0){
                                codeList = new CodeList();
                                codeList.setBaseId(baseId);
                                codeList.setCode(object.get(0));
                                codeList.setCreatorId(userId);
                                list.add(codeList);
                                count++;
                            }
                            if (count == page) {
                                // 添加到线程集合中去执行
                                if(!list.isEmpty()){
                                    Future<String> future =  executorService.submit(threadUpListDB(futures.size(),list));
                                    futures.add(future);
                                }
                                count = 0;
                                list = new ArrayList<CodeList>();
                            }
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {

                            if(!list.isEmpty()){
                                Future<String> future =  executorService.submit(threadUpListDB(futures.size(),list));
                                futures.add(future);
                            }

                        }
                    });
            reader.read();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error( "异常信息 : [{}]" , e);
            }
        }
    }

    /**
     * @Description: (获取线程执行结果)
     * @param thread
     * @param list
     * @[param] [thread, list]
     * @return java.util.concurrent.Callable<java.lang.String>
     * @author:  bin.yang
     * @date:  2019/4/15 12:19 PM
     */
    private Callable<String> threadUpListDB(int thread, List<CodeList> list) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                int count = list.size();
                bathPageInsertList(thread,list);
                return String.valueOf(count);
            }
        };
    }

    /**
     * @Description: (批量插入数据库)
     * @param thread
     * @param list
     * @[param] [thread, list]
     * @return void
     * @author:  bin.yang
     * @date:  2019/4/15 12:18 PM
     */
    private void bathPageInsertList(int thread, List<CodeList> list) {
        long s = System.currentTimeMillis();
        try {
            Thread.sleep(100);
            codeMapper.bathPageInsertList(list);
            long e = System.currentTimeMillis();
            log.info("线程" + thread + "批量事务插入：" + list.size() + " 个 , 总共耗时-----------------------:" + (e - s) + "ms!");
        } catch (InterruptedException e) {
            log.error(" 异常信息 : [{}]" , e);
            log.info("批量执行插入异常:>>" + list.size());
            throw new RuntimeException();
        }
    }



}

/**
 * @Author: bin.yang
 * @Date: 2019/4/16 10:47 AM
 *
 * @Description:  异步同步库存
 */
@Slf4j
@Component
class CodeAsynTask{

    @Autowired
    private CodeBiz codeBiz;

    @Autowired
    private CodeListMapper codeListMapper;

    private ExecutorService executor = SingleThreadPoolExecutor.getSingleThreadPool();

    public void asynInsertStock(Integer baseId, Integer maxId){

         executor.submit(new Runnable() {

             @Override
             public void run() {
                 log.info("异步执行同步库存数量");
                 int i = codeBiz.updateOperateStocks(baseId, maxId);
                 if(i > 0){
                     log.info("库存同步完成");
                     Date date = new Date();
                     int s = codeListMapper.updateCodeStatusByBaseId(baseId, date.toLocaleString());
                     if(s > 0){
                         log.info("状态同步完成");
                     }else {
                         log.error("状态同步失败 : 串码库ID[{}]",baseId);
                     }
                 }else {
                     log.error("库存同步失败 : 串码库ID[{}]",baseId);
                 }

             }
         });
    }
}
