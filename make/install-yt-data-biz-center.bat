rd yt_data_biz_center_jar /S /Q
md yt_data_biz_center_jar
pushd yt_data_biz_center_jar
md bin
md lib
md config

copy ..\..\..\thirdparty\dubbo\*.* bin\
copy ..\..\..\thirdparty\druid\*.* bin\
copy ..\..\..\thirdparty\log4j\*.* bin\
copy ..\..\..\thirdparty\mybatis\*.* bin\
copy ..\..\..\thirdparty\mysql\*.* bin\
copy ..\..\..\thirdparty\spring\*.* bin\
copy ..\..\..\thirdparty\ssm\*.* bin\

copy ..\..\..\yt-platform\framework\lib\1.2\yt-utility.jar bin\
copy ..\..\..\yt-platform\framework\lib\1.2\yt-db.jar bin\
copy ..\..\..\yt-platform\framework\lib\1.2\yt-data.jar bin\

copy ..\..\lib\yt-data-biz-center.jar lib\
copy ..\..\yt-data-biz-center\config\*.* config\
copy ..\startup-yt-data-biz-center.bat startup.bat
popd