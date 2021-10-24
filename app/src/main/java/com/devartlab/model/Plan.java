package com.devartlab.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class Plan {

        @SerializedName("Table")
        @Expose
        private List<PlanModel> planModels = null;
        @SerializedName("Table1")
        @Expose
        private List<Object> table1 = null;
        @SerializedName("Table2")
        @Expose
        private List<Object> table2 = null;

        public List<PlanModel> getPlanModels() {
            return planModels;
        }

        public void setPlanModels(List<PlanModel> planModels) {
            this.planModels = planModels;
        }

        public List<Object> getTable1() {
            return table1;
        }

        public void setTable1(List<Object> table1) {
            this.table1 = table1;
        }

        public List<Object> getTable2() {
            return table2;
        }

        public void setTable2(List<Object> table2) {
            this.table2 = table2;
        }
    }
