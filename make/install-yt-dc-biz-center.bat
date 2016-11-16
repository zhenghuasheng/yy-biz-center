rd yt_dc_biz_center_jar /S /Q
md yt_dc_biz_center_jar
pushd yt_dc_biz_center_jar
md bin
md lib
md jetty-config
md web


copy ..\..\..\thirdparty\dubbo\*.* bin\
copy ..\..\..\thirdparty\fastjson\*.* bin\
copy ..\..\..\thirdparty\log4j\*.* bin\
copy ..\..\..\thirdparty\spring\*.* bin\
copy ..\..\..\thirdparty\spring-mvc\*.* bin\
copy ..\..\..\thirdparty\ssm\*.* lib\
copy ..\..\..\thirdparty\ytcar\yt-auto-api.jar lib\
copy ..\..\..\thirdparty\ytcar\info_service.jar lib\
copy ..\..\..\thirdparty\rocketmq\*.* lib\
copy ..\..\..\yt-framework\lib\yt-checkrepeat.jar lib\

copy ..\..\..\yt-framework\lib\yt-utility.jar bin\
copy ..\..\..\yt-platform\framework\lib\1.2\yt-dc-api.jar bin\
copy ..\..\..\yt-platform\framework\lib\1.2\yt-data-user.jar lib\
copy ..\..\..\yt-platform\framework\lib\1.2\yt-dc-user.jar lib\

copy ..\..\lib\yt-data-biz-center.jar lib\
copy ..\..\lib\yt-dc-biz-center.jar lib\


xcopy ..\..\yt-dc-biz-center\jetty-config\*.* jetty-config\ /s
xcopy ..\..\yt-dc-biz-center\web\*.* web\ /s
copy ..\startup-yt-dc-biz-center.bat startup.bat
popd