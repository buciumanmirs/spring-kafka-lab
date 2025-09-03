SUBDIRS := $(wildcard */)

.PHONY: all clean build build-skip-test

all: clean build-skip-test

clean:
	@for dir in $(SUBDIRS); do \
	  if [ -f $$dir/build.gradle ] || [ -f $$dir/build.gradle.kts ]; then \
	    echo "===> Cleaning $$dir"; \
	    (cd $$dir && gradle clean); \
	  fi \
	done

build-skip-test:
	@for dir in $(SUBDIRS); do \
	  if [ -f $$dir/build.gradle ] || [ -f $$dir/build.gradle.kts ]; then \
	    echo "===> Building $$dir"; \
	    (cd $$dir && gradle build -x test); \
	  fi \
	done

build:
	@for dir in $(SUBDIRS); do \
	  if [ -f $$dir/build.gradle ] || [ -f $$dir/build.gradle.kts ]; then \
	    echo "===> Building $$dir"; \
	    (cd $$dir && gradle build -x test); \
	  fi \
	done
