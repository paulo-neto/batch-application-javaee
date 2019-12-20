package com.pauloneto.batchapplication.job.scheduller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.logging.Logger;

@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class IniciarJobs {

    private static final String TIMER_NAME = "IniciarJobs";

    @Inject
    private Logger logger;

    @Resource
    private TimerService timerService;

    @PostConstruct
    public void init(){
        ScheduleExpression expression = configurarScheduleExpression();
        TimerConfig config = new TimerConfig(TIMER_NAME, false);
        this.timerService.createCalendarTimer(expression, config);
        logger.info(String.format("[IniciarJobs] - Configuração do @TimeOut: %s",expression));
    }

    @Timeout
    public void iniciarJobs(){
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        jobOperator.start("batch-jobs",null);



    }

    private ScheduleExpression configurarScheduleExpression() {
        return new ScheduleExpression().dayOfMonth(20).hour("*").minute("*/1");// expression default
    }
}
