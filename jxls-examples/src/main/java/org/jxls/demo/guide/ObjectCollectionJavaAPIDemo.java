package org.jxls.demo.guide;

import org.jxls.area.XlsArea;
import org.jxls.command.EachCommand;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Leonid Vysochyn
 *         Date: 11/4/13
 */
public class ObjectCollectionJavaAPIDemo {
    static Logger logger = LoggerFactory.getLogger(ObjectCollectionJavaAPIDemo.class);

    public static void main(String[] args) throws ParseException, IOException {
        logger.info("Running Object Collection JavaAPI demo");
        List<Employee> employees = generateSampleEmployeeData();
        try(InputStream is = ObjectCollectionDemo.class.getResourceAsStream("object_collection_javaapi_template.xls")) {
            try (OutputStream os = new FileOutputStream("target/object_collection_javaapi_output.xls")) {
                Transformer transformer = TransformerFactory.createTransformer(is, os);
                XlsArea xlsArea = new XlsArea("Template!A1:D4", transformer);
                XlsArea employeeArea = new XlsArea("Template!A4:D4", transformer);
                EachCommand employeeEachCommand = new EachCommand("employee", "employees", employeeArea);
                xlsArea.addCommand("A4:D4", employeeEachCommand);
                Context context = new Context();
                context.putVar("employees", employees);
                xlsArea.applyAt(new CellRef("Result!A1"), context);
                transformer.write();
            }
        }
    }

    private static List<Employee> generateSampleEmployeeData() throws ParseException {
        List<Employee> employees = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        employees.add( new Employee("Elsa", dateFormat.parse("1970-Jul-10"), 1500, 0.15) );
        employees.add( new Employee("Oleg", dateFormat.parse("1973-Apr-30"), 2300, 0.25) );
        employees.add( new Employee("Neil", dateFormat.parse("1975-Oct-05"), 2500, 0.00) );
        employees.add( new Employee("Maria", dateFormat.parse("1978-Jan-07"), 1700, 0.15) );
        employees.add( new Employee("John", dateFormat.parse("1969-May-30"), 2800, 0.20) );
        return employees;
    }
}
