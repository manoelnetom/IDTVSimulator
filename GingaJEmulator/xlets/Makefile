# Compile all Xlets

EMULATOR_HOME := ".."

CLASSPATH := "$(EMULATOR_HOME)/lib/cake.jar:$(EMULATOR_HOME)/lib/metouia.jar:$(EMULATOR_HOME)/lib/nanoxml-2.2.3.jar:$(EMULATOR_HOME)/lib/javassist.jar:$(EMULATOR_HOME)/lib/javatv.jar:$(EMULATOR_HOME)/lib/jmf:$(EMULATOR_HOME)/build/classes:$(EMULATOR_HOME)"

PROJECTS	:= $(shell find -maxdepth 1 -type d | sed -e 's/\.[\/]*//g')

OK          = \033[30;32mOK\033[m
FAILED      = \033[30;31mFAILED\033[m

all:
	@for i in $(PROJECTS); do \
		echo -n "Compiling $$i" ... ; find $$i -iname "*.java" | xargs $(JAVA_HOME)/bin/javac -classpath $$i:$(CLASSPATH) && echo " $(OK)" || echo " $(FAILED)"; \
	done

clean:
	@find -iname "*.class" -exec rm {} \; && echo "Cleanup $(OK)!\n" 
