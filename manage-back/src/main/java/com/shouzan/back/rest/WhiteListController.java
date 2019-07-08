package com.shouzan.back.rest;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.WhiteBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.constant.thread.SingleThreadPoolExecutor;
import com.shouzan.back.entity.white.WhiteBase;
import com.shouzan.back.entity.white.WhiteUser;
import com.shouzan.back.mapper.WhiteMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author: bin.yang
 * @Date: 2019/3/28 12:45
 * @Description:
 */
@Controller
@RequestMapping("/white")
@Validated
@Slf4j
public class WhiteListController {

    @Autowired
    private WhiteBiz whiteBiz;

    @Autowired
    private SystemUserController sysUserController;

    @Autowired
    private WhiteMapper whiteMapper;

    // 批次导入处理数据条数
    private static final int PAGE = 2000;

    /**
     * @Description: (分页查询白名单库数据集合)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/28 4:39 PM
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                          SearchSatisfy search){
        try {
            int count = whiteBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<WhiteUser> list = whiteBiz.queryPageList(search);
            log.info("request : 白名单活动列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("白名单库分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("获取信息失败 , 请刷新页面再次尝试 .");
        }
    }

    /**
     * @Description: (创建白名单)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/28 4:40 PM
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse add(@Valid WhiteBase entity){
        try {
            Integer userId = sysUserController.getUserId();
            entity.setCreatorId(userId);
            log.info("request : 创建白名单库 , 名单库信息[{}] ",entity);
            return whiteBiz.createWhiteBase(entity);
        } catch (Exception e) {
            log.error("新增白名单信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("创建失败,请重新创建 !");
        }
    }

    /**
     * @Description: (修改白名单库信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/28 4:47 PM
     */
    @RequestMapping(value = "/updateInfo",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateInfo(@Valid WhiteBase entity){
        try {
            Integer userId = sysUserController.getUserId();
            entity.setLastEditId(userId);
            log.info("request : 修改白名单库信息 , 名单库信息[{}] ",entity);
            return whiteBiz.updateWhiteBase(entity);
        } catch (Exception e) {
            log.error("修改白名单信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改失败,请重新修改 !");
        }
    }

    /**
     * @Description: (银行白名单用户导入)
     * @param upFile
     * @param id
     * @[param] [upFile, id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/3 10:29 AM
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse importUserExcel(@RequestParam("upFile")MultipartFile upFile,
                                              @NotNull(message = "ID不能为NULL") Integer id) throws Exception {
        long s = System.currentTimeMillis();

        //获取输入流
        InputStream in = upFile.getInputStream();

        //获取用户ID
        Integer userId = sysUserController.getUserId();

        //获取白名单库标识码
        String sign = whiteMapper.findBaseSignById(id);

        //多线程批量处理
        ExecutorService executorService = null;
        try {

            // 创建线程池
            List<Future<String>> futureList = new ArrayList<Future<String>>();
            int processors = Runtime.getRuntime().availableProcessors();

            // 获取单例线程池
            executorService = SingleThreadPoolExecutor.getFixedThreadPool(processors * 2);

            // 批量读取数据,并存入数据库
            readExcelList(executorService, futureList, in, userId, sign);

            //获取线程执行结果
            int threadNum = 0;
            int Total = 0;
            for (Future<String> future : futureList) {
                while (true){
                    if(future.isDone() && !future.isCancelled()){
                        threadNum++;
                        Total += Integer.parseInt(future.get());
                        break;
                    }else {
                        Thread.sleep(200);
                    }
                }
            }
            long e = System.currentTimeMillis();
            log.info("白名单数据导入完成 >> : 使用线线程数 " + threadNum + " 个 , 导入数据 " + Total + " 条 . 共耗时--: " + (e - s)/1000 + "ms!");
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("白名单数据导入完成 >> :  导入数据 " + Total + " 条 . 共耗时--: " + (e - s)/1000 + "ms!");
        } catch (Exception e) {
            log.error("白名单信息导入异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("白名单数据录入失败 , 请重新录入 ! ");
        }
    }

    /**
     * @Description: (分批读取 , 分批处理  )
     * @param executorService
     * @param futureList
     * @param sheet
     * @param userId
     * @param sign
     * @param index
     * @[param] [executorService, futureList, sheet, userId, sign, index]
     * @return boolean
     * @author:  bin.yang
     * @date:  2019/4/3 10:29 AM
     */

    /**
     * @Author: bin.yang
     * @Date: 2019/4/29 2:27 PM
     *
     * @Description: 已弃用  , 原因  大量数据 造成OOM异常
     */
    /*private boolean readExcelList(ExecutorService executorService, List<Future<String>> futureList, Sheet sheet, Integer userId, String sign, int index) {

        // 每次读取条数
        int page = PAGE;
        // 开始位置
        int start = ( page * index ) + 1;
        // 数据统计
        int count = 0;
        boolean status = false;
        // 获取总条数
        int totalRows = sheet.getPhysicalNumberOfRows();
        List<WhiteUser> list = new ArrayList<>();
        WhiteUser user ;
        // 循环每一行
        for (int i = start; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            if (row == null){
                continue;
            }
            Cell cell = row.getCell(0);
            // excel数字类型转换
            DecimalFormat df = new DecimalFormat("0");
            String value = df.format(cell.getNumericCellValue());
            user = new WhiteUser();
            user.setPhone(value);
            user.setCreatorId(userId);
            list.add(user);
            count++;
            if (count == page) {
                status = true;
                break;
            }
        }
        // 添加到线程集合中去执行
        if(!list.isEmpty()){
            Future<String> future =  executorService.submit(threadUpListDB(futureList.size(),list, sign));
            futureList.add(future);
        }
        return status;
    }*/

    /**
     * @Description: 批量处理插入
     * @param executorService
     * @param futureList
     * @param in
     * @param userId
     * @param sign
     * @[param] [executorService, futureList, in, userId, sign]
     * @return void
     * @author:  bin.yang
     * @date:  2019/4/29 2:28 PM
     */
    private void readExcelList(ExecutorService executorService, List<Future<String>> futureList, InputStream in, Integer userId, String sign) {
        try {
            ExcelReader reader = new ExcelReader(in, ExcelTypeEnum.XLSX, null,
                    new AnalysisEventListener<List<String>>() {

                        List<WhiteUser> list = new ArrayList<WhiteUser>();

                        WhiteUser user ;

                        // 每次读取条数
                        int page = PAGE;

                        int count = 0;

                        @Override
                        public void invoke(List<String> object, AnalysisContext context) {
                            if(object.get(0) != null && context.getCurrentRowNum() != 0){
                                user = new WhiteUser();
                                user.setPhone(object.get(0));
                                user.setCreatorId(userId);
                                list.add(user);
                                count++;
                            }
                            if (count == page) {
                                // 添加到线程集合中去执行
                                if(!list.isEmpty()){
                                    Future<String> future =  executorService.submit(threadUpListDB(futureList.size(),list, sign));
                                    futureList.add(future);
                                }
                                count = 0;
                                list = new ArrayList<WhiteUser>();
                            }
                        }
                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {

                            if(!list.isEmpty()){
                                Future<String> future =  executorService.submit(threadUpListDB(futureList.size(),list, sign));
                                futureList.add(future);
                            }

                        }
                    });
            reader.read();
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("异常信息 : [{}]" , e);
            }
        }
    }

    /**
     * @Description: (获取线程执行结果)
     * @param thread
     * @param list
     * @param sign
     * @[param] [thread, list, sign]
     * @return java.util.concurrent.Callable<java.lang.String>
     * @author:  bin.yang
     * @date:  2019/4/3 10:30 AM
     */
    private Callable<String> threadUpListDB(int thread, List<WhiteUser> list, String sign) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                int count = list.size();
                batchPageInsertList(thread, list, sign);
                return String.valueOf(count);
            }
        };
    }

    /**
     * @Description: (数据批次存入数据库)
     * @param thread
     * @param list
     * @param sign
     * @[param] [thread, list, sign]
     * @return void
     * @author:  bin.yang
     * @date:  2019/4/3 10:30 AM
     */
    private void batchPageInsertList(int thread, List<WhiteUser> list, String sign) {
        long stime = System.currentTimeMillis();
        try {
            Thread.sleep(100);
            whiteMapper.batchInsertList(list, sign);
            long etime = System.currentTimeMillis();
            log.info("线程" + thread + "批量事务插入：" + list.size() + " 个 , 总共耗时-----------------------:" + (etime - stime) + "ms!");
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            log.error("批量执行插入异常:>>" + list.size());
            throw new RuntimeException();
        }
    }

}
