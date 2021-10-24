package com.devartlab.ui.main.ui.callmanagement.report;

import com.devartlab.data.room.plan.PlanEntity;

public interface ReportInterface {

      void makeCall(PlanEntity planEntity);
      void startVisit(PlanEntity planEntity);
      void startSocialVisit(PlanEntity planEntity);
      void deleteExtra(PlanEntity planEntity);
      void order(PlanEntity planEntity);
      void collect(PlanEntity planEntity);
}
