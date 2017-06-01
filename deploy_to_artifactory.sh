mvn deploy:deploy-file -Durl=http://artifactory.cg.ru/artifactory/libs-releases-local -DrepositoryId=central \
  -Dfile=dist/bundles/biz/aQute/bnd/biz.aQute.bndlib/3.3.305/biz.aQute.bndlib-3.3.305.jar \
  -DpomFile=dist/bundles/biz/aQute/bnd/biz.aQute.bndlib/3.3.305/biz.aQute.bndlib-3.3.305.pom \
  -Dsources=dist/bundles/biz/aQute/bnd/biz.aQute.bndlib/3.3.305/biz.aQute.bndlib-3.3.305-sources.jar \
  -Djavadoc=dist/bundles/biz/aQute/bnd/biz.aQute.bndlib/3.3.305/biz.aQute.bndlib-3.3.305-javadoc.jar