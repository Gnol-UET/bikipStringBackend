package uet.k59t.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uet.k59t.model.Faculty;
import uet.k59t.model.Teacher;
import uet.k59t.repository.FacultyRepository;
import uet.k59t.repository.TeacherRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    public void createFaculty() {
        try {
            if(facultyRepository.findByFacultyName("Cong nghe thong tin") == null){
                Faculty cntt = new Faculty("Cong nghe thong tin", "Xuan Thuy");
                Faculty dtvt = new Faculty("Dien tu vien thong", "Xuan Thuy");
                Faculty vlkt = new Faculty("Vat ly ky thuat", "Xuan Thuy");
                Faculty chktvtdh = new Faculty("Co hoc dien tu va tu dong hoa", "Xuan Thuy");
                facultyRepository.save(cntt);
                facultyRepository.save(vlkt);
                facultyRepository.save(chktvtdh);
                facultyRepository.save(dtvt);
            }
        }catch (AopInvocationException e ){
            Faculty cntt = new Faculty("Cong nghe thong tin", "Xuan Thuy");
            Faculty dtvt = new Faculty("Dien tu vien thong", "Xuan Thuy");
            Faculty vlkt = new Faculty("Vat ly ky thuat", "Xuan Thuy");
            Faculty chktvtdh = new Faculty("Co hoc dien tu va tu dong hoa", "Xuan Thuy");
            facultyRepository.save(cntt);
            facultyRepository.save(vlkt);
            facultyRepository.save(chktvtdh);
            facultyRepository.save(dtvt);
        }

    }

    public void createTeacher(MultipartFile multipartFile) {
        try
        {
//            MultipartFile  file;
            byte [] byteArr=multipartFile.getBytes();
//            InputStream inputStream = new ByteArrayInputStream(byteArr);
            ByteArrayInputStream file = new ByteArrayInputStream(byteArr);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            boolean isFirstLine = true;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                Teacher teacher =  new Teacher();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:

                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
//                            newTeacher.setUsername();
                            if(cell.getColumnIndex() == 1) teacher.setUsername(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 2) teacher.setPassword(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 3) teacher.setUnit(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 4) teacher.setEmail(cell.getStringCellValue());
                            break;
                    }
                }
                Teacher existedTeacher = teacherRepository.findByUsername(teacher.getUsername());
                if(existedTeacher == null){
                    teacherRepository.save(teacher);
                }
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
