package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkProfile {
    public class Example {

        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("hr_of_work")
        @Expose
        private Integer hrOfWork;
        @SerializedName("salary")
        @Expose
        private Integer salary;
        @SerializedName("right")
        @Expose
        private String right;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getHrOfWork() {
            return hrOfWork;
        }

        public void setHrOfWork(Integer hrOfWork) {
            this.hrOfWork = hrOfWork;
        }

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }
}
