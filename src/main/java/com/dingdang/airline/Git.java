package com.dingdang.airline;

import java.util.List;

import io.airlift.airline.Arguments;
import io.airlift.airline.Cli;
import io.airlift.airline.Cli.CliBuilder;
import io.airlift.airline.Command;
import io.airlift.airline.Help;
import io.airlift.airline.Option;
import io.airlift.airline.OptionType;

public class Git {

	public static void main(String[] args) {
		CliBuilder<Runnable> builder =Cli.<Runnable>builder("git")
				.withDescription("git command")
				.withDefaultCommand(Help.class)
				.withCommands(Help.class,Add.class);
		Cli<Runnable> gitParser = builder.build();
		gitParser.parse(args).run();
	}
	public static class GitCommand implements Runnable
    {
        @Option(type = OptionType.GLOBAL, name = "-v", description = "Verbose mode")
        public boolean verbose;

        public void run()
        {
            System.out.println(getClass().getSimpleName());
        }
    }
	@Command(name = "add", description = "Add file contents to the index")
    public static class Add extends GitCommand
    {
        @Arguments(description = "Patterns of files to be added")
        public List<String> patterns;

        @Option(name = "-i", description = "Add modified contents interactively.")
        public boolean interactive;
        
        @Override
        public void run() {
        	System.out.println(patterns);
        }
    }

}
