# the stuff the readme claims needs to be packaged - fwvd
tar czf solr-vs-analyzer-package.tar.gz --format=gnutar -C target/lib VnCoreNLP-1.1.1.jar activation-1.1.1.jar commons-io-2.1.jar jaxb-api-2.3.0.jar jaxb-core-2.3.0.jar jaxb-impl-2.3.0.jar -C .. solr-vn-analyzer-1.0.jar
