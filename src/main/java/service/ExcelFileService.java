package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.iface.FileDao;

import com.google.common.collect.Maps;

@Service("excelFileService")
public class ExcelFileService extends FileService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelFileService.class) ;
	
	@Autowired
	private FileDao fileDao;
	
	@Override
	public Map<String, String> parse(File uploadFile) {
		Map<String,String> data = Maps.newHashMap();
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(new FileInputStream(uploadFile));
			int numberOfName = wb.getNumberOfNames();
			for(int i=0;i<numberOfName; i++){
				Name name =  wb.getNameAt(i);
			    // retrieve the cell at the named range and test its contents
			    AreaReference aref = new AreaReference(name.getRefersToFormula());
			    org.apache.poi.ss.util.CellReference[] crefs = aref.getAllReferencedCells();
			    Sheet s = wb.getSheet(name.getSheetName());
//		        for (int j=0; i<crefs.length; j++) {
//			        Row r = s.getRow(crefs[j].getRow());
//			        Cell c = r.getCell(crefs[j].getCol());
//			        data.put(name.getNameName()+(j==0?"":j), c.getStringCellValue());
//			    }
			    Row r = s.getRow(crefs[0].getRow());
		        Cell c = r.getCell(crefs[0].getCol());
		        data.put(name.getNameName(), c.getStringCellValue());
			}
			fileDao.saveTempData(data);
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage());
			data = null;
		}finally{
			while(true){
				try {
					wb.close();
					break;
				} catch (IOException e) {
					LOGGER.error(e.getLocalizedMessage());
				}
			}
		}
		return data;
	}
}
