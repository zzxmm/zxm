package com.shouzan.back.util;

import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Advert;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.Store;
import com.shouzan.back.mapper.MerchantsMapper;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted;

/**   
 * @ClassName:  ExcelUtil   
 * @Description:excel解析类   
 * @author:
 * @date:   2017年12月22日 下午3:17:02   
 *    
 * @Copyright:2017
 *
 */

public class ExcelUtil {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	
	private static final DataFormatter FORMATTER = new DataFormatter();
	
    private final static String xls = "xls";  
    
    private final static String xlsx = "xlsx";

    @Autowired
    private MerchantsMapper merchantsMapper;

	/**
	 * @Description 方法描述:解析Excel
	 * @Author      作        者:
	 * @UpdateDate  更新时间:2017年12月25日-下午7:39:28
	 *
	 * @param file 上传的Excel文件
	 * @return      
	 * Workbook   
	 */
	public static Workbook getWorkbook(MultipartFile file) {
		String fileName = file.getOriginalFilename(); 
		Workbook book = null;
		try {
			InputStream fileStream = file.getInputStream();
			if(fileName.endsWith(xls)){
				book = new HSSFWorkbook(fileStream);  
            }else if(fileName.endsWith(xlsx)){  
            	book = new XSSFWorkbook(fileStream);  
            } 
		} catch (IOException e) {
			e.printStackTrace();
			log.error("excel解析失败： " + e.getMessage());
		}
		return book;
	}
	
    /**
     * @Description 方法描述:验证上传的文件是否是Excel文件
     * @Author      作        者:
     * @UpdateDate  更新时间:2017年12月25日-下午7:40:03
     *
     * @param file 上传的文件
     * @throws IOException      
     * void   
     */
    public static void checkFile(MultipartFile file) throws IOException{  
        //判断文件是否存在  
        if(null == file){  
            throw new FileNotFoundException("文件不存在！");  
        }  
        //获得文件名  
        String fileName = file.getOriginalFilename();  
        //判断文件是否是excel文件  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
            throw new IOException(fileName + "不是excel文件");  
        }  
    } 
	
	/**
	 * @Description 方法描述:获取Excel中的数据
	 * @Author      作        者:
	 * @UpdateDate  更新时间:2017年12月25日-下午7:56:04
	 *
	 * @param row 行数据
	 * @param a 第几列
	 * @return      
	 * String   
	 */
	public static String getContent(Row row, int a){
		Cell cell = row.getCell(a);
		String value = FORMATTER.formatCellValue(cell);
		return value.trim();
	}
	
	/**
	 * @Description 方法描述:获取单元格内容
	 * @Author      作        者:
	 * @UpdateDate  更新时间:2017年12月25日-下午8:01:40
	 *
	 * @param cell  单元格对象
	 * @return      转化为字符串的单元格内容
	 * String   
	 */
	public static String getCellContent(Cell cell) {
		if(cell == null){
			return "";
		}
		String value = FORMATTER.formatCellValue(cell);
		return value.trim();
	}
	
	
	public static void export(HttpServletResponse response, String filename, String[] headers, List<String[]> list) {
		
		try {
			// response.setContentType("application/x-msdownload");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((filename + ".xls").getBytes("gbk"), "iso-8859-1"));
//			response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("账单信息");
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 15);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 11);
			// 把字体应用到当前的样式
			style.setFont(font);
			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			int a = 1;
			for (String[] str : list) {
				row = sheet.createRow(a);
				for (int i = 0; i < str.length; i++) {
					HSSFCell cell = row.createCell(i);
					HSSFRichTextString richString = new HSSFRichTextString(str[i]);
					richString.applyFont(font);
					cell.setCellValue(richString);
				}
				a++;
			}

			workbook.write(response.getOutputStream());
			workbook.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

    /**
     * @Description: ( Excel读取 操作)
     * @param in
     * @[param] [in]
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author:  bin.yang
     * @date:  2018/11/28 11:16 AM
     */
    public static List<List<String>> readExcel(InputStream in)
            throws IOException {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(in);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String sheetName = wb.getSheetAt(0).getSheetName();

        /** 得到第一个sheet */
        Sheet sheet = wb.getSheetAt(0);

        /** 得到Excel的行数 */
        int totalRows = sheet.getPhysicalNumberOfRows();

        /** 得到Excel的列数 */
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 循环Excel的行 */
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    HSSFDataFormatter hssfDataFormatter = new HSSFDataFormatter();
                    cellValue = hssfDataFormatter.formatCellValue(cell);
                    switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC: // 数字
                        if (isCellDateFormatted(cell)) {    // 判断是日期换是时间属性
                            Date theDate = cell.getDateCellValue();
                            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            cellValue = dff.format(theDate);
                        }else{
                            DecimalFormat df = new DecimalFormat("0");
                            cellValue = df.format(cell.getNumericCellValue());
                        }
                        break;
                    case CELL_TYPE_STRING: // 字符串
                        cellValue = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BOOLEAN: // Boolean
                        cellValue = cell.getBooleanCellValue() + "";
                        break;
                    case Cell.CELL_TYPE_FORMULA: // 公式
                        cellValue = cell.getCellFormula() + "";
                        break;
                    case Cell.CELL_TYPE_BLANK: // 空值
                        cellValue = "";
                        break;
                    case Cell.CELL_TYPE_ERROR: // 故障
                        cellValue = "非法字符";
                        break;
                    default:
                        cellValue = "未知类型";
                        break;
                    }
                }
                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    /**
     * @Description: (广告福利社 数据获取)
     * @param is
     * @[param] [is]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/29 11:03 AM
     */
    public static List<Advert> getExcelList(InputStream is)
            throws IOException {
        List<List<String>> list = readExcel(is);
        String title = list.get(0).get(0);
        List<Advert> listBean = new ArrayList<Advert>();
        for (int i = 1; i < list.size(); i++) {
            Advert bean = new Advert();
            List<String> listStr = list.get(i);
            if(listStr.get(0).equals("") || listStr.get(0) == null){
                break;
            }
            for (int j = 0; j < listStr.size(); j++) {
                switch(j){
                    case 0:bean.setBankId(Integer.parseInt(listStr.get(j))); break;
                    case 1:bean.setAdvertName(listStr.get(j)); break;
                    case 2:bean.setAdvertUrl(listStr.get(j)); break;
                    case 3:
                        if("".equals(listStr.get(j))){break;}
                        bean.setUpshelfTime(listStr.get(j));break;
                    case 4:
                        if("".equals(listStr.get(j))){break;}
                        bean.setDownshelfTime(listStr.get(j));break;
                }
            }
            listBean.add(bean);
        }
        return listBean;
    }


    /**
     * @Description: (根据输入流生成实体集合)
     * @param is
     * @[param] [is]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2018/11/28 14:16 PM
     */
    public static ObjectRestResponse getList(InputStream is)
            throws IOException {
        List<List<String>> list = readExcel(is);
        String title = list.get(0).get(0);
        if(title.indexOf("商户") >= 0 ){
            List<Merchants> listBean = new ArrayList<Merchants>();
            for (int i = 2; i < list.size(); i++) {
                Merchants bean = new Merchants();
                List<String> listStr = list.get(i);
                if(listStr.get(0).equals("") || listStr.get(0) == null){
                    break;
                }
                for (int j = 0; j < listStr.size(); j++) {
                    switch(j){
                        case 0:bean.setId(Integer.parseInt(listStr.get(j))); break;
                        case 1:bean.setMerName(listStr.get(j)); break;
                        case 2:bean.setMerShotName(listStr.get(j)); break;
                        case 3:Byte bs = null;
                            if(listStr.get(j).indexOf("关联") >= 0){ bs = 0; }
                            if(listStr.get(j).indexOf("解绑") >= 0){ bs = 1; }
                            bean.setServicenum(bs); break;
                        case 4:Byte bn = null;
                            if(listStr.get(j).indexOf("启用") >= 0){ bn = 1; }
                            if(listStr.get(j).indexOf("停用") >= 0){ bn = 0; }
                            bean.setEnableState(bn); break;
                        case 5:Byte bi = null;
                            if(listStr.get(j).indexOf("是") >= 0){ bi = 1; }
                            if(listStr.get(j).indexOf("否") >= 0){ bi = 0; }
                            bean.setIsSelect(bi); break;
                        case 6:bean.setMerType(Integer.parseInt(listStr.get(j))); break;
                        case 7:bean.setWechatMchId(listStr.get(j)); break;
                        case 8:bean.setPriority(Integer.parseInt(listStr.get(j))); break;
                        case 9:bean.setMerIntroduce(listStr.get(j)); break;
                        case 10:bean.setLinkmanName(listStr.get(j)); break;
                        case 11:bean.setLinkmanTel(listStr.get(j)); break;
                        case 12:bean.setLinkmanWechat(listStr.get(j)); break;
                        case 13:bean.setServiceTelNumber(listStr.get(j)); break;
                    }
                }
                listBean.add(bean);
            }
            return new ObjectRestResponse<Merchants>().rows(listBean).rel(Response.SUCCESS).msg(title);
        }else if(title.indexOf("门店") >= 0){
            List<Store> listBean = new ArrayList<Store>();
            for (int i = 2; i < list.size(); i++) {
                Store bean = new Store();
                List<String> listStr = list.get(i);
                if(listStr.get(0).equals("") || listStr.get(0) == null){
                    break;
                }
                for (int j = 0; j < listStr.size(); j++) {
                    switch(j){
                        case 0:bean.setStoreName(listStr.get(j));break;
                        case 1:bean.setBusinessHours(listStr.get(j));break;
                        case 2:Byte bs = null;
                            if(listStr.get(j).indexOf("关联") >= 0){ bs = 0; }
                            if(listStr.get(j).indexOf("解绑") >= 0){ bs = 1; }
                            bean.setServicenum(bs); break;
                        case 3:Byte bn = null;
                            if(listStr.get(j).indexOf("营业") >= 0){ bn = 0; }
                            if(listStr.get(j).indexOf("停业") >= 0){ bn = 1; }
                            bean.setStoreState(bn); break;
                        case 4:bean.setMerCenterId(Integer.parseInt(listStr.get(j)));break; // 本地数据商户ID
                        case 5:bean.setProvince(listStr.get(j)); break;
                        case 6:bean.setCity(listStr.get(j)); break;
                        case 7:bean.setArea(listStr.get(j)); break;
                        case 8:bean.setAddress(listStr.get(j));
                    }
                }
                listBean.add(bean);
            }
            return new ObjectRestResponse<Store>().rows(listBean).rel(Response.SUCCESS).msg(title);
        }else if(title.indexOf("银行") >= 0){
            List<Bank> listBean = new ArrayList<Bank>();
            for (int i = 2; i < list.size(); i++) {
                Bank bean = new Bank();
                List<String> listStr = list.get(i);
                if(listStr.get(0).equals("") || listStr.get(0) == null){
                    break;
                }
                for (int j = 0; j < listStr.size(); j++) {
                    switch(j){
                        case 0:bean.setBankName(listStr.get(j));break;
                        case 1:bean.setBankShotName(listStr.get(j));break;
                        case 2:Byte bn = null;
                            if(listStr.get(j).indexOf("停用") >= 0){ bn = 0; }
                            if(listStr.get(j).indexOf("启用") >= 0){ bn = 1; }
                            bean.setEnableState(bn); break;
                        case 3:bean.setServiceTelNumber(listStr.get(j));break;
                        case 4:bean.setCreditcardApplyUrl(listStr.get(j));break;
                        case 5:bean.setPriority(Integer.parseInt(listStr.get(j)));break;
                        case 6:bean.setBankLinkmanName(listStr.get(j));break;
                        case 7:bean.setBankLinkmanTel(listStr.get(j));break;
                        case 8:bean.setBankLinkmanWechat(listStr.get(j));break;
                        case 9:bean.setBankLinkmanPosition(listStr.get(j));break;
                        case 10:Byte bi = null;
                            if(listStr.get(j).indexOf("是") >= 0){ bi = 1; }
                            if(listStr.get(j).indexOf("否") >= 0){ bi = 0; }
                            bean.setIsSelect(bi); break;
                    }
                }
                listBean.add(bean);
            }
            return new ObjectRestResponse<Bank>().rows(listBean).rel(Response.SUCCESS).msg(title);
        }
        return new ObjectRestResponse().rel(Response.FAILURE).msg("解析失败 , 请重新上传文件");
    }

    /**
     * @Description: (获取sheet页)
     * @param in
     * @[param] [in]
     * @return org.apache.poi.ss.usermodel.Sheet
     * @author:  bin.yang
     * @date:  2019/4/15 10:56 AM
     */
    public static Sheet getExcelSheet(InputStream in) throws IOException{
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sheetName = wb.getSheetAt(0).getSheetName();
        /** 得到第一个sheet */
        Sheet sheet = wb.getSheetAt(0);
        return sheet;
    }
}
