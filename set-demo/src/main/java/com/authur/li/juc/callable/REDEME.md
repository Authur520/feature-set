Callable的使用：
2. Callable cal = new Callable();
3. FutureTask futureTask = new FureTask(cal)
4. new Thread(futureTask).start;
5. 拿到返回值：futureTask.get();