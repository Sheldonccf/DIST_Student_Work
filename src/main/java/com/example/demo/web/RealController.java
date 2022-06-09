package com.example.demo.web;

//import com.example.demo.Batch.toDoItemProcessor;
import com.example.demo.ExcelExport.ExcelExport;
import com.example.demo.model.Todo;
//import com.example.demo.service.JobLaunchService;
import com.example.demo.service.JobLaunchService;
import com.example.demo.service.Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v-1.0")
@Api(tags = "任务表管理接口")
public class RealController {
    @Autowired
    private Service service;
    @Autowired
    private JobLaunchService jobLauncher;



    /** constructor注射service层 **/
    //@Autowired
    //public RealController(Service service, JobLauncher jobLauncher, Job task) {
    //    this.service = service;
    //    this.jobLauncher = jobLauncher;
    //    this.task = task;
    //}

    @GetMapping(value = "/tasks")
    @ApiOperation("查找所有任务")
    public List<Todo> getAllList() {
        return service.getAllList();
    }

    @PostMapping ("/tasks")
    @ApiOperation("添加任务")
    public void addTask(Todo todolist) {
        if (!service.existById(todolist.getId())) {
            service.save(todolist);
        }
    }

    @DeleteMapping ("/tasks/{id}")
    @ApiOperation("删除任务")
    public void deleteTask(@RequestParam("id") int theId) {
        if (service.existById(theId)) {
            service.deleteById(theId);
        }
    }

    @GetMapping(value ="/tasks/{id}")
    @ApiOperation("查找任务")
    public Todo findTask(@RequestParam("id") int theId) {
        return service.findById(theId);
    }

    @PutMapping(value ="/tasks/{id}")
    @ApiOperation("修改任务")
    public Todo updateTask(@RequestParam("id") int theId, Todo original) {
        Todo item =service.findById(theId);

        if (service.existById(theId)) {
            item.setName(original.getName());
            item.setDeadline(original.getDeadline());
            item.setStatus(original.isStatus());
            service.save(item);
        }
        return service.findById(theId);
    }

    @GetMapping("/downloads")
    @ApiOperation("从mysql导出")
    public void exportToExcel() {
        List<Todo> all = service.getAllList();
        ExcelExport excelFileExporter = new ExcelExport(all, "ToDoList.xlsx" );
        excelFileExporter.writeToExcel();
    }

    @GetMapping("/uploads")
    @ApiOperation("读取csv并存入mysql数据库")
    public BatchStatus upload() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException,
            JobRestartException {

        return jobLauncher.load();
    }
}
