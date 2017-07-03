package com.dingdang.java8.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.netty.util.concurrent.Future;

@RunWith(SpringRunner.class)
public class CompletableFutureTest {
	@Test
	public void testExceptionUnchecked() {
		final CompletableFuture<Integer> future = new CompletableFuture<>();
		// future.complete(200);
		future.completeExceptionally(new Exception("test error"));
		System.out.println(future.join());// join应用在函数式编程(不推荐checked exception)
	}

	@Test
	public void testExceptionChecked() throws Exception {
		final CompletableFuture<Integer> future = new CompletableFuture<>();
		// future.complete(200);
		future.completeExceptionally(new Exception("test error"));
		System.out.println(future.get());
	}

	@Test
	public void testInit1() throws Exception {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			int i = 1 / 0;
			return i;
		});
		future.join();
	}

	@Test
	public void testInit2() throws Exception {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			System.out.println("1111111111111");
		});
	}

	@Test
	public void testWhenComplete() throws Exception {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 100;
		});
		CompletableFuture<Integer> result = future.whenComplete((t, e) -> {
			System.out.println(t);
		});
		System.out.println(result.get());
	}

	// 返回的CompletableFuture不是原来的，而是一个新的
	// 当原先的CompletableFuture的值计算完成或者抛出异常的时候，
	//会触发这个CompletableFuture对象的计算，结果由BiFunction参数计算而得。
	//因此这组方法兼有whenComplete和转换的两个功能。
	@Test
	public void testHandle() throws Exception {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 100;
		});
		CompletableFuture<String> result = future.handle((t, e) -> {
			return "String";
		});
		System.out.println(result.get());
	}
	//它们与handle方法的区别在于handle方法会处理正常计算值和异常，因此它可以屏蔽异常，避免异常继续抛出。
	//而thenApply方法只是用来处理正常值，因此一旦有异常就会抛出。
	@Test
	public void testThenApply() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<String> f =  future.thenApplyAsync(i -> i * 10).thenApply(i -> i.toString());
		System.out.println(f.get()); //"1000"
	}
	@Test
	public void testThenAccept() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<Void> f=future.thenAccept(System.out::println);
		System.out.println(f.get());
	}
	//纯消费，没有返回值
	@Test
	public void testThenAcceptBoth() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<Void> f=future.thenAcceptBoth(CompletableFuture.completedFuture(200), (x,y)->{
			System.out.println(x*y);
		});
		System.out.println(f.get());
	}
	//runAfterBoth是当两个CompletionStage都正常完成计算的时候,执行一个Runnable，这个Runnable并不使用计算的结果。
	@Test
	public void testRunAfterBoth() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<Void> f=future.runAfterBoth(CompletableFuture.completedFuture(200), ()->{
			System.out.println("独立的计算");
		});
		System.out.println(f.get());
	}	
	@Test
	public void testThenRun() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<Void> f=future.thenRun(()->{
			System.out.println("独立的计算");
		});
		System.out.println(f.get());
	}	
	@Test
	public void testThenCompose() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<Integer> f=future.thenCompose(i->{
			return CompletableFuture.supplyAsync(()->{
				return i*10;
			});
		});
		System.out.println(f.get());
	}	
	//其实从功能上来讲,它们的功能更类似thenAcceptBoth，只不过thenAcceptBoth是纯消费，它的函数参数没有返回值，而thenCombine的函数参数fn有返回值。
	@Test
	public void testThenCombine() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<String> f=future.thenCombine(CompletableFuture.completedFuture("test"),(x,y)->{
			return x+y;
		});
		System.out.println(f.get());
	}	
	@Test
	public void testAcceptEither() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<Void> f=future.acceptEither(CompletableFuture.completedFuture(200),t->{
			System.out.println(t+"123");
		});
		System.out.println(f.get());
	}
	@Test
	public void testApplyToEither() throws Exception{
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
		    return 100;
		});
		CompletableFuture<String> f=future.applyToEither(CompletableFuture.completedFuture(200),t->{
			return "test";
		});
		System.out.println(f.get());
	}
	@Test
	public void testAllOf() throws Exception{
		CompletableFuture<Void> f=CompletableFuture.allOf(CompletableFuture.completedFuture(100),CompletableFuture.completedFuture(200));
		System.out.println(f.get());
	}
	@Test
	public void testAnyOf() throws Exception{
		CompletableFuture<Object> f=CompletableFuture.anyOf(CompletableFuture.completedFuture(100),CompletableFuture.completedFuture(200));
		System.out.println(f.get());
	}
	
	//有这样一个需求，将多个CompletableFuture组合成一个CompletableFuture，
		//这个组合后的CompletableFuture的计算结果是个List,它包含前面所有的CompletableFuture的计算结果
	/*public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
	       CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
	       return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
	}
	public static <T> CompletableFuture<Stream<T>> sequence1(Stream<CompletableFuture<T>> futures) {
	       List<CompletableFuture<T>> futureList = futures.filter(f -> f != null).collect(Collectors.toList());
		return sequence(futureList);
	}*/
	public static <T> CompletableFuture<T> toCompletable(Future<T> future, Executor executor) {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            return future.get();
	        } catch (InterruptedException | ExecutionException e) {
	            throw new RuntimeException(e);
	        }
	    }, executor);
	}
}
