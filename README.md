# My Example Assets #

This example is provided to corrispond with the OmniSper, Inc. [blog post](http://omnispear.com/blog/2014/02/06/designing-website-asset-caching-with-scalatra/) on Scalatra ssset management. It provides a method for cache busting using build numbers from your CI system.

## Build & Run ##

### Run with assets managed by WRO4J:

$ ./sbt -DBUILD_NUMBER=234 "container:start" "~ ;copy-resources;aux-compile"

### Run with assets delivered statically:

$ ./sbt "container:start" "~ ;copy-resources;aux-compile"

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.
