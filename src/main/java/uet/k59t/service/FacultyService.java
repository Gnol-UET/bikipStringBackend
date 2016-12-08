package uet.k59t.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uet.k59t.model.Faculty;
import uet.k59t.model.MailMail;
import uet.k59t.model.Student;
import uet.k59t.model.Teacher;
import uet.k59t.repository.FacultyRepository;
import uet.k59t.repository.StudentRepository;
import uet.k59t.repository.TeacherRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;
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
        try {
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
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
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
                            if(cell.getColumnIndex() == 0) teacher.setUsername(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 1) teacher.setUnit(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 2) teacher.setEmail(cell.getStringCellValue());
                            break;
                    }
                }
                Teacher existedTeacher = teacherRepository.findByUsername(teacher.getUsername());
                if(existedTeacher == null){
                    teacher.setPassword(UUID.randomUUID().toString().substring(0,7));
                    teacherRepository.save(teacher);
                }
            }
            file.close();

            ApplicationContext context =
                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
            MailMail mm = (MailMail) context.getBean("mailMail");
            String sender="sendergmailid@gmail.com";//write here sender gmail id
            String[] receiver = new String[sheet.getLastRowNum()];
//            for (long i = 0; i < sheet.getLastRowNum(); i++) {
//
//                if(teacherRepository.findOne(Long.valueOf(i+1)) != null){
//                    Teacher temp = teacherRepository.findOne(Long.valueOf(i+1));
//                    receiver[(int) i]= temp.getEmail();
//                    mm.sendMail(sender,receiver[(int) i],"Mật khẩu","Đây là tài khoản của bạn \n" +
//                            "Tên đăng nhập: " + temp.getEmail() +"\n" +
//                            "Mật khẩu: " + temp.getPassword()+"\n" +
//                            "Chào mừng bạn đến với Đại học Công Nghệ");
//                }
//            }

            System.out.println("1");
//            mm.sendMail(sender,receiver,"hello, my name is Long","higgsup company welcomes you . we want to work");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createStudent(MultipartFile multipartFile) {
        try {
//            MultipartFile  file;
            byte [] byteArr=multipartFile.getBytes();
//            InputStream inputStream = new ByteArrayInputStream(byteArr);
            ByteArrayInputStream file = new ByteArrayInputStream(byteArr);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(1);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            boolean isFirstLine = true;
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                Student student = new Student();
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
                            if(cell.getColumnIndex() == 0) student.setUsername(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 1) student.setGrade(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 2) student.setClassname(cell.getStringCellValue());
                            if(cell.getColumnIndex() == 3) student.setEmail(cell.getStringCellValue());
                            break;
                    }
                }
                Student existedStudent = studentRepository.findByUsername(student.getUsername());
                if(existedStudent == null){
                    student.setPassword(UUID.randomUUID().toString().substring(0,7));
                    studentRepository.save(student);
                }
            }
            file.close();

            ApplicationContext context =
                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
            MailMail mm = (MailMail) context.getBean("mailMail");
            String sender="sendergmailid@gmail.com";//write here sender gmail id
            String[] receiver = new String[sheet.getLastRowNum()];
//            for (long i = 0; i < sheet.getLastRowNum(); i++) {
//
//                if(studentRepository.findOne(Long.valueOf(i+1)) != null){
//                    Student temp = studentRepository.findOne(Long.valueOf(i+1));
//                    receiver[(int) i]= temp.getEmail();
//                    mm.sendMail(sender,receiver[(int) i],"Mật khẩu","Đây là tài khoản của bạn \n" +
//                            "Tên đăng nhập: " + temp.getEmail() +"\n" +
//                            "Mật khẩu: " + temp.getPassword()+"\n" +
//                            "Chào mừng bạn đến với Đại học Công Nghệ");
//                }
//            }

//            mm.sendMail(sender,receiver,"hello, my name is Long","higgsup company welcomes you . we want to work");
            System.out.println("1");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
