package com.authur.funccollect.controller;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class demo {


    /**
     * 项目的目录
     */
    String projectName = "C:/Users/chuan/IdeaProjects/fashion_shoe";


    /**
     * 要统计的文件类型
     */
    String str[] = new String[]{"java", "xml", "html"};

    /**
     * 要查找的文字
     */
    // String find = "最";
    String find[] = new String[]{"价格","订单"};


    List<File> list = new ArrayList<File>();

    int linenumber = 0;

    FileReader fr = null;
    BufferedReader br = null;
    static Byte[] aByte = new Byte[1*1024*1024];

    public static void main(String args[]) throws IOException, InterruptedException {
//        new demo().counter();
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 24,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(2),
//                new ThreadFactory() {
//                    @Override
//                    public Thread newThread(Runnable r) {
//                        Thread t = new Thread();
//                        t.setName("teat");
//                        return t;
//                    }
//                },
//                new ThreadPoolExecutor.AbortPolicy());
//
//        executor.execute(()->{
//            for (int i = 0; i < 10; i++) {
//                System.out.println("aaa");
//            }
//        });
//        executor.shutdown();//关闭线程池，但是线程池内的任务还在执行
//        executor.shutdownNow();//关闭线程池，并且停止正在运行的内容
//        System.out.println(0b11111111111111111111111111111111);
//
//        new Thread().start();

//        计算效率
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("task1");
//        Thread.sleep(200);
//        stopWatch.stop();
//
//        stopWatch.start("task2");
//        Thread.sleep(400);
//        stopWatch.stop();
//        System.out.println("shuchu:"+stopWatch.prettyPrint());


        ArrayList<Object> objects = new ArrayList<>();
        int count = 0;
        try {
            while (true){
                objects.add(aByte);
                count = count + 1;
            }
        }catch (Error e){
            System.out.println("count"+ count);
            e.printStackTrace();
        }

    }




    public void counter() throws IOException {

        Date date = new Date();

        /**
         * 创建csv文件
         * */
        File createFile = new File("C:/Users/chuan/IdeaProjects/fashion_shoe/minganci.csv");
        if(!createFile.exists()){
            createFile.getParentFile().mkdirs();
            createFile.createNewFile();
        }

        File file = new File(projectName);
        File files[] = null;
        files = file.listFiles();

        addFile(files);
        readLinePerFile(createFile);
        // System.out.println("总行数:" + linenumber + "行");
        // System.out.println("统计用时:" + (new Date().getTime() - date.getTime()) + "毫秒");
        // System.out.println("文件数量:" + list.size() + "个");
    }


    // 将所有符合查找类型的文件都加入到文件列表中
    public void addFile(File file[]) {
        for (int index = 0; index < file.length; index++) {
            if(file[index].isFile()){
                String name = file[index].getName().substring(file[index].getName().lastIndexOf(".") + 1);
                for(int i = 0; i < str.length; i++){
                    if(name.equals(str[i])){
                        list.add(file[index]);
                        break;
                    }
                }
            } else {
                addFile(file[index].listFiles());
            }
        }
    }

    // 查找写入csv
    public void readLinePerFile(File createFile) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(createFile));
        try {
            for (int j = 0; j < find.length; j++) {
                for (File s : list) {
                    int num = 0;
                    if (s.isDirectory()) {
                        continue;
                    }
                    fr = new FileReader(s);
                    br = new BufferedReader(fr);
                    String i = "";
                    boolean flag = true;
                    int a =1;
                    while ((i = br.readLine()) != null) {
                        num++;
                        if (find != null && !find.equals("")) {
                            //循环敏感词
                            if (i.indexOf("/*")!=-1 || i.indexOf("/**") !=-1 ){
                                a =2;
                                continue;
                            }else if (i.indexOf("*/") !=-1){
                                a=1;
                                continue;
                            }
                            if (i.indexOf(find[j]) != -1 && i.indexOf("//") == -1 && a==1) {
                                StringBuffer sb = new StringBuffer();
                                sb.append(find[j]).append(",文件：" + s.getAbsolutePath()).append("中第" + num + "行");//最，文件：路径 中第1行
                                bufferedWriter.write(String.valueOf(sb));
                                bufferedWriter.newLine();
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedWriter.close();
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                }
            }
        }
    }




    // public static void main(String[] args) {
    //
    //
    // }



}
