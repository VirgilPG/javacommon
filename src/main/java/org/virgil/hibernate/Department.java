package org.virgil.hibernate;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 2017/3/2.
 */
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "eid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "d_name")
    private String dname; //部门名称
    @OneToMany(mappedBy = "department")
//    @JoinColumn(name = "departmentId")
    private List<Employee> employeeList = new ArrayList<Employee>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
