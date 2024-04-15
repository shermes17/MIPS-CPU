JFLAGS = -g
JC = javac
RM = rm -f

# Function to recursively find Java source files
rwildcard=$(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2) $(filter $(subst *,%,$2),$d))

# Collect all Java files from the specified directories
CLASSES = $(call rwildcard,project2/controllers/,*.java) \
          $(call rwildcard,project2/models/,*.java) \
          $(call rwildcard,project2/views/,*.java) \
          project2/CPU.java

# Pattern rule for compiling Java files
%.class: %.java
	$(JC) $(JFLAGS) $<

# Default rule to build all classes
default: classes

classes: $(CLASSES:.java=.class)

run: classes
	java project2.CPUApp

clean:
	$(RM) $(CLASSES:.java=.class)
