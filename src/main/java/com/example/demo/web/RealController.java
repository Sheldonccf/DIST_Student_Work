package com.example.demo.web;

import com.example.demo.Batch.toDoItemProcessor;
import com.example.demo.ExcelExport.ExcelExport;
import com.example.demo.model.Todo;
import com.example.demo.service.JobLaunchService;
import com.example.demo.service.Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/toDo")
@Api(tags = "任务表管理接口")
public class RealController {

    private Service service;

    private JobLaunchService jobLaunchService;

    private toDoItemProcessor task;

    /** constructor注射service层 **/
    @Autowired
    public RealController(Service service, JobLaunchService jobLaunchService, toDoItemProcessor task) {
        this.service = service;
        this.jobLaunchService = jobLaunchService;
        this.task = task;
    }

    @GetMapping(value = "/form")
    @ApiOperation("显示所有任务表")
    public String getAllList(Model theModel) {

        //add to the spring model bound to the html
        theModel.addAttribute("todolist", service.getAllList());

        return "form";
    }

    @GetMapping(value ="/showFormForAdd")
    @ApiOperation("显示添加任务表页面")
    public String showFormForAdd(Model theModel) {

        Todo todolist = new Todo();

        theModel.addAttribute("todolist", todolist);

        return "add";
    }

    @PostMapping ("/add")
    @ApiOperation("添加任务")
    public String addTask(@ModelAttribute("todolist") Todo todolist) {

        service.save(todolist);

        return "redirect:/toDo/form";
    }

    @GetMapping("/delete")
    @ApiOperation("删除任务")
    public String delete(@RequestParam("id") int theId) {

        service.deleteById(theId);

        return "redirect:/toDo/form";
    }

    @GetMapping(value ="/showFormForUpdate")
    @ApiOperation("显示修改任务表页面")
    public String showFormForUpdate(@RequestParam("id") int theId,
                                    Model theModel) {

        Todo todolist = service.findById(theId);

        theModel.addAttribute("todolist", todolist);

        return "update";
    }

    @PostMapping(value ="/update")
    @ApiOperation("修改任务")
    public String updateTask(@ModelAttribute("todolist") Todo todolist) {

        service.save(todolist);

        return "redirect:/toDo/form";
    }
    @GetMapping("/users/export/excel")
    @ApiOperation("导出到excel")
    public String exportToDoList() {

        //read directly from the service layer
        List<Todo> all = service.getAllList();
        ExcelExport excelFileExporter = new ExcelExport(all, "ToDoList.xlsx" );
        excelFileExporter.writeToExcel();

        return "redirect:/toDo/form";
    }

    @GetMapping("/jdbc")
    @ApiOperation("读取csv并存入mysql数据库")
    public String launchJobUseFlatFileWithJdbcBatchItemWriter() {

        jobLaunchService.launchJob(task.thisJob());
        return "redirect:/toDo/form";
    }





}
