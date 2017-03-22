import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.virgil.hibernate.Department;
import org.virgil.hibernate.Employee;

import java.io.IOException;
import java.util.List;

/**
 * Created by virgil on 2017/3/2.
 */
public class TestAction1 {

    private static SessionFactory sessionFactory = null;
    ObjectMapper mapper = new ObjectMapper();

    static {
        //读取classpath中applicationContext.xml配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取session中配置的sessionFactory对象
        sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
    }

    @Test
    public void testSave() {
        //创建一个部门对象
        Department d1 = new Department();
        d1.setDname("sxx我还要");

        //创建两个员工对象
        Employee e1 = new Employee();
        e1.setName("vv1");
        Employee e2 = new Employee();
        e2.setName("vv2");

        //设置对象关联
        d1.getEmployeeList().add(e1);
        d1.getEmployeeList().add(e2);
        e1.setDepartment(d1);
        e2.setDepartment(d1);

        //获取Session
        Session session = sessionFactory.openSession();
        //开始事务
        Transaction t = session.beginTransaction();
        try {
            //添加数据
            session.save(d1);
            session.save(e1);
            session.save(e2);

            //提交事务
            t.commit();
        } catch (RuntimeException e) {
            //有异常则回滚事务
            t.rollback();
            e.printStackTrace();
        } finally {
            //关闭session
            session.close();
        }
    }

    @Test
    public void testList() {
        //获取Session
        Session session = sessionFactory.openSession();
        //开始事务
        Transaction t = session.beginTransaction();

        String hql = "from Department";
        Query query = session.createQuery(hql);
        List<Department> list = query.list();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
            List<Employee> employeeList = list.get(i).getEmployeeList();
            for (Employee ee : employeeList) {
                System.out.println(ee.getName());
            }
        }

        t.commit();

    }

}
