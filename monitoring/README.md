# The montorign stack: prometheus / grafana

I use a third party prometheus/grafana that works very well:

```bash
git clone git@github.com:stefanprodan/dockprom.git

cd dockprom

git checkout v9.0.0

ADMIN_USER='admin' ADMIN_PASSWORD='admin' ADMIN_PASSWORD_HASH='$2a$14$1l.IozJx7xQRVmlkEQ32OeEEfP5mRxTpbDTCTcXRqn19gXD8YK1pO' docker-compose up -d
```

And after its up, I can access (the IP addr of my VM is `192.168.1.16`):
* Prometheus at : http://192.168.1.16:9090/ : username and password are `admin/admin`
* Grafana at : http://192.168.1.16:3000/ : username and password are `admin/admin`, it then asks me to reset the admi user password.



Then for prometheus to be able to reach through network the tomcat:

```bash
export PROMETHEUS_DOCK_NETWRK='dockprom_monitor-net'
docker network connect ${PROMETHEUS_DOCK_NETWRK} tomcat

docker exec -it prometheus sh -c 'wget -S http://tomcat:8080/pestoapp/api/fruit'

docker exec -it prometheus sh -c 'wget -S http://tomcat:8088/metrics'

```

When we have first scrapped the tomcat metrics, we get this list of metrics we can work with:

```bash
# HELP jmx_config_reload_failure_total Number of times configuration have failed to be reloaded.
# TYPE jmx_config_reload_failure_total counter
jmx_config_reload_failure_total 0.0
# HELP jmx_config_reload_success_total Number of times configuration have successfully been reloaded.
# TYPE jmx_config_reload_success_total counter
jmx_config_reload_success_total 0.0
# HELP jmx_exporter_build_info JMX Exporter build information
# TYPE jmx_exporter_build_info gauge
jmx_exporter_build_info{name="jmx_prometheus_javaagent",version="1.0.1"} 1
# HELP jmx_scrape_cached_beans Number of beans with their matching rule cached
# TYPE jmx_scrape_cached_beans gauge
jmx_scrape_cached_beans 0.0
# HELP jmx_scrape_duration_seconds Time this JMX scrape took, in seconds.
# TYPE jmx_scrape_duration_seconds gauge
jmx_scrape_duration_seconds 0.0
# HELP jmx_scrape_error Non-zero if this scrape failed.
# TYPE jmx_scrape_error gauge
jmx_scrape_error 0.0
# HELP jvm_buffer_pool_capacity_bytes Bytes capacity of a given JVM buffer pool.
# TYPE jvm_buffer_pool_capacity_bytes gauge
jvm_buffer_pool_capacity_bytes{pool="direct"} 81919.0
jvm_buffer_pool_capacity_bytes{pool="mapped"} 0.0
jvm_buffer_pool_capacity_bytes{pool="mapped - 'non-volatile memory'"} 0.0
# HELP jvm_buffer_pool_used_buffers Used buffers of a given JVM buffer pool.
# TYPE jvm_buffer_pool_used_buffers gauge
jvm_buffer_pool_used_buffers{pool="direct"} 7.0
jvm_buffer_pool_used_buffers{pool="mapped"} 0.0
jvm_buffer_pool_used_buffers{pool="mapped - 'non-volatile memory'"} 0.0
# HELP jvm_buffer_pool_used_bytes Used bytes of a given JVM buffer pool.
# TYPE jvm_buffer_pool_used_bytes gauge
jvm_buffer_pool_used_bytes{pool="direct"} 81919.0
jvm_buffer_pool_used_bytes{pool="mapped"} 0.0
jvm_buffer_pool_used_bytes{pool="mapped - 'non-volatile memory'"} 0.0
# HELP jvm_classes_currently_loaded The number of classes that are currently loaded in the JVM
# TYPE jvm_classes_currently_loaded gauge
jvm_classes_currently_loaded 9141.0
# HELP jvm_classes_loaded_total The total number of classes that have been loaded since the JVM has started execution
# TYPE jvm_classes_loaded_total counter
jvm_classes_loaded_total 9141.0
# HELP jvm_classes_unloaded_total The total number of classes that have been unloaded since the JVM has started execution
# TYPE jvm_classes_unloaded_total counter
jvm_classes_unloaded_total 0.0
# HELP jvm_compilation_time_seconds_total The total time in seconds taken for HotSpot class compilation
# TYPE jvm_compilation_time_seconds_total counter
jvm_compilation_time_seconds_total 9.756
# HELP jvm_gc_collection_seconds Time spent in a given JVM garbage collector in seconds.
# TYPE jvm_gc_collection_seconds summary
jvm_gc_collection_seconds_count{gc="G1 Concurrent GC"} 4
jvm_gc_collection_seconds_sum{gc="G1 Concurrent GC"} 0.009
jvm_gc_collection_seconds_count{gc="G1 Old Generation"} 0
jvm_gc_collection_seconds_sum{gc="G1 Old Generation"} 0.0
jvm_gc_collection_seconds_count{gc="G1 Young Generation"} 11
jvm_gc_collection_seconds_sum{gc="G1 Young Generation"} 0.082
# HELP jvm_memory_committed_bytes Committed (bytes) of a given JVM memory area.
# TYPE jvm_memory_committed_bytes gauge
jvm_memory_committed_bytes{area="heap"} 6.9206016E7
jvm_memory_committed_bytes{area="nonheap"} 6.5667072E7
# HELP jvm_memory_init_bytes Initial bytes of a given JVM memory area.
# TYPE jvm_memory_init_bytes gauge
jvm_memory_init_bytes{area="heap"} 1.7825792E8
jvm_memory_init_bytes{area="nonheap"} 7667712.0
# HELP jvm_memory_max_bytes Max (bytes) of a given JVM memory area.
# TYPE jvm_memory_max_bytes gauge
jvm_memory_max_bytes{area="heap"} 2.829058048E9
jvm_memory_max_bytes{area="nonheap"} -1.0
# HELP jvm_memory_objects_pending_finalization The number of objects waiting in the finalizer queue.
# TYPE jvm_memory_objects_pending_finalization gauge
jvm_memory_objects_pending_finalization 0.0
# HELP jvm_memory_pool_allocated_bytes_total Total bytes allocated in a given JVM memory pool. Only updated after GC, not continuously.
# TYPE jvm_memory_pool_allocated_bytes_total counter
jvm_memory_pool_allocated_bytes_total{pool="CodeHeap 'non-nmethods'"} 1607552.0
jvm_memory_pool_allocated_bytes_total{pool="CodeHeap 'non-profiled nmethods'"} 3428480.0
jvm_memory_pool_allocated_bytes_total{pool="CodeHeap 'profiled nmethods'"} 1.1513344E7
jvm_memory_pool_allocated_bytes_total{pool="Compressed Class Space"} 5195000.0
jvm_memory_pool_allocated_bytes_total{pool="G1 Eden Space"} 5.07510784E8
jvm_memory_pool_allocated_bytes_total{pool="G1 Old Gen"} 1.7644936E7
jvm_memory_pool_allocated_bytes_total{pool="G1 Survivor Space"} 1.4140784E7
jvm_memory_pool_allocated_bytes_total{pool="Metaspace"} 4.1623192E7
# HELP jvm_memory_pool_collection_committed_bytes Committed after last collection bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_collection_committed_bytes gauge
jvm_memory_pool_collection_committed_bytes{pool="G1 Eden Space"} 3.3554432E7
jvm_memory_pool_collection_committed_bytes{pool="G1 Old Gen"} 2.9360128E7
jvm_memory_pool_collection_committed_bytes{pool="G1 Survivor Space"} 6291456.0
# HELP jvm_memory_pool_collection_init_bytes Initial after last collection bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_collection_init_bytes gauge
jvm_memory_pool_collection_init_bytes{pool="G1 Eden Space"} 1.2582912E7
jvm_memory_pool_collection_init_bytes{pool="G1 Old Gen"} 1.65675008E8
jvm_memory_pool_collection_init_bytes{pool="G1 Survivor Space"} 0.0
# HELP jvm_memory_pool_collection_max_bytes Max bytes after last collection of a given JVM memory pool.
# TYPE jvm_memory_pool_collection_max_bytes gauge
jvm_memory_pool_collection_max_bytes{pool="G1 Eden Space"} -1.0
jvm_memory_pool_collection_max_bytes{pool="G1 Old Gen"} 2.829058048E9
jvm_memory_pool_collection_max_bytes{pool="G1 Survivor Space"} -1.0
# HELP jvm_memory_pool_collection_used_bytes Used bytes after last collection of a given JVM memory pool.
# TYPE jvm_memory_pool_collection_used_bytes gauge
jvm_memory_pool_collection_used_bytes{pool="G1 Eden Space"} 0.0
jvm_memory_pool_collection_used_bytes{pool="G1 Old Gen"} 1.7644936E7
jvm_memory_pool_collection_used_bytes{pool="G1 Survivor Space"} 6291456.0
# HELP jvm_memory_pool_committed_bytes Committed bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_committed_bytes gauge
jvm_memory_pool_committed_bytes{pool="CodeHeap 'non-nmethods'"} 2555904.0
jvm_memory_pool_committed_bytes{pool="CodeHeap 'non-profiled nmethods'"} 3276800.0
jvm_memory_pool_committed_bytes{pool="CodeHeap 'profiled nmethods'"} 1.0682368E7
jvm_memory_pool_committed_bytes{pool="Compressed Class Space"} 5701632.0
jvm_memory_pool_committed_bytes{pool="G1 Eden Space"} 3.3554432E7
jvm_memory_pool_committed_bytes{pool="G1 Old Gen"} 2.9360128E7
jvm_memory_pool_committed_bytes{pool="G1 Survivor Space"} 6291456.0
jvm_memory_pool_committed_bytes{pool="Metaspace"} 4.3450368E7
# HELP jvm_memory_pool_init_bytes Initial bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_init_bytes gauge
jvm_memory_pool_init_bytes{pool="CodeHeap 'non-nmethods'"} 2555904.0
jvm_memory_pool_init_bytes{pool="CodeHeap 'non-profiled nmethods'"} 2555904.0
jvm_memory_pool_init_bytes{pool="CodeHeap 'profiled nmethods'"} 2555904.0
jvm_memory_pool_init_bytes{pool="Compressed Class Space"} 0.0
jvm_memory_pool_init_bytes{pool="G1 Eden Space"} 1.2582912E7
jvm_memory_pool_init_bytes{pool="G1 Old Gen"} 1.65675008E8
jvm_memory_pool_init_bytes{pool="G1 Survivor Space"} 0.0
jvm_memory_pool_init_bytes{pool="Metaspace"} 0.0
# HELP jvm_memory_pool_max_bytes Max bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_max_bytes gauge
jvm_memory_pool_max_bytes{pool="CodeHeap 'non-nmethods'"} 5836800.0
jvm_memory_pool_max_bytes{pool="CodeHeap 'non-profiled nmethods'"} 1.22912768E8
jvm_memory_pool_max_bytes{pool="CodeHeap 'profiled nmethods'"} 1.22908672E8
jvm_memory_pool_max_bytes{pool="Compressed Class Space"} 1.073741824E9
jvm_memory_pool_max_bytes{pool="G1 Eden Space"} -1.0
jvm_memory_pool_max_bytes{pool="G1 Old Gen"} 2.829058048E9
jvm_memory_pool_max_bytes{pool="G1 Survivor Space"} -1.0
jvm_memory_pool_max_bytes{pool="Metaspace"} -1.0
# HELP jvm_memory_pool_used_bytes Used bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_used_bytes gauge
jvm_memory_pool_used_bytes{pool="CodeHeap 'non-nmethods'"} 1601152.0
jvm_memory_pool_used_bytes{pool="CodeHeap 'non-profiled nmethods'"} 3097344.0
jvm_memory_pool_used_bytes{pool="CodeHeap 'profiled nmethods'"} 9354752.0
jvm_memory_pool_used_bytes{pool="Compressed Class Space"} 5419584.0
jvm_memory_pool_used_bytes{pool="G1 Eden Space"} 6291456.0
jvm_memory_pool_used_bytes{pool="G1 Old Gen"} 1.7644936E7
jvm_memory_pool_used_bytes{pool="G1 Survivor Space"} 6291456.0
jvm_memory_pool_used_bytes{pool="Metaspace"} 4.2910296E7
# HELP jvm_memory_used_bytes Used bytes of a given JVM memory area.
# TYPE jvm_memory_used_bytes gauge
jvm_memory_used_bytes{area="heap"} 3.0227848E7
jvm_memory_used_bytes{area="nonheap"} 6.2382552E7
# HELP jvm_runtime_info JVM runtime info
# TYPE jvm_runtime_info gauge
jvm_runtime_info{runtime="OpenJDK Runtime Environment",vendor="Eclipse Adoptium",version="21.0.8+9-LTS"} 1
# HELP jvm_threads_current Current thread count of a JVM
# TYPE jvm_threads_current gauge
jvm_threads_current 33.0
# HELP jvm_threads_daemon Daemon thread count of a JVM
# TYPE jvm_threads_daemon gauge
jvm_threads_daemon 30.0
# HELP jvm_threads_deadlocked Cycles of JVM-threads that are in deadlock waiting to acquire object monitors or ownable synchronizers
# TYPE jvm_threads_deadlocked gauge
jvm_threads_deadlocked 0.0
# HELP jvm_threads_deadlocked_monitor Cycles of JVM-threads that are in deadlock waiting to acquire object monitors
# TYPE jvm_threads_deadlocked_monitor gauge
jvm_threads_deadlocked_monitor 0.0
# HELP jvm_threads_peak Peak thread count of a JVM
# TYPE jvm_threads_peak gauge
jvm_threads_peak 34.0
# HELP jvm_threads_started_total Started thread count of a JVM
# TYPE jvm_threads_started_total counter
jvm_threads_started_total 37.0
# HELP jvm_threads_state Current count of threads by state
# TYPE jvm_threads_state gauge
jvm_threads_state{state="BLOCKED"} 0.0
jvm_threads_state{state="NEW"} 0.0
jvm_threads_state{state="RUNNABLE"} 8.0
jvm_threads_state{state="TERMINATED"} 0.0
jvm_threads_state{state="TIMED_WAITING"} 5.0
jvm_threads_state{state="UNKNOWN"} 0.0
jvm_threads_state{state="WAITING"} 20.0
# HELP process_cpu_seconds_total Total user and system CPU time spent in seconds.
# TYPE process_cpu_seconds_total counter
process_cpu_seconds_total 14.43
# HELP process_max_fds Maximum number of open file descriptors.
# TYPE process_max_fds gauge
process_max_fds 1048576.0
# HELP process_open_fds Number of open file descriptors.
# TYPE process_open_fds gauge
process_open_fds 58.0
# HELP process_resident_memory_bytes Resident memory size in bytes.
# TYPE process_resident_memory_bytes gauge
process_resident_memory_bytes 2.64118272E8
# HELP process_start_time_seconds Start time of the process since unix epoch in seconds.
# TYPE process_start_time_seconds gauge
process_start_time_seconds 1.762091161495E9
# HELP process_virtual_memory_bytes Virtual memory size in bytes.
# TYPE process_virtual_memory_bytes gauge
process_virtual_memory_bytes 6.650642432E9
# HELP tomcat_bytesreceived_total Tomcat global bytesReceived
# TYPE tomcat_bytesreceived_total counter
tomcat_bytesreceived_total{port="8080",protocol="http-nio"} 0.0
# HELP tomcat_bytessent_total Tomcat global bytesSent
# TYPE tomcat_bytessent_total counter
tomcat_bytessent_total{port="8080",protocol="http-nio"} 3181.0
# HELP tomcat_errorcount_total Tomcat global errorCount
# TYPE tomcat_errorcount_total counter
tomcat_errorcount_total{port="8080",protocol="http-nio"} 4.0
# HELP tomcat_maxtime_total Tomcat global maxTime
# TYPE tomcat_maxtime_total counter
tomcat_maxtime_total{port="8080",protocol="http-nio"} 283.0
# HELP tomcat_processingtime_total Tomcat global processingTime
# TYPE tomcat_processingtime_total counter
tomcat_processingtime_total{port="8080",protocol="http-nio"} 304.0
# HELP tomcat_requestcount_total Tomcat global requestCount
# TYPE tomcat_requestcount_total counter
tomcat_requestcount_total{port="8080",protocol="http-nio"} 5.0
# HELP tomcat_serverinfo_total Tomcat server release identifier Catalina:name=null,type=Server,attribute=serverInfo
# TYPE tomcat_serverinfo_total counter
tomcat_serverinfo_total{serverinfo="Apache Tomcat/10.1.48"} 1.0
# HELP tomcat_servlet_errorcount_total Tomcat servlet errorCount total
# TYPE tomcat_servlet_errorcount_total counter
tomcat_servlet_errorcount_total{module="localhost/pestoapp",servlet="FacesServlet"} 0.0
tomcat_servlet_errorcount_total{module="localhost/pestoapp",servlet="com.example.GreetingPagesServlet"} 0.0
tomcat_servlet_errorcount_total{module="localhost/pestoapp",servlet="com.example.GreetingServlet"} 0.0
tomcat_servlet_errorcount_total{module="localhost/pestoapp",servlet="com.example.RestActivator"} 0.0
tomcat_servlet_errorcount_total{module="localhost/pestoapp",servlet="default"} 0.0
tomcat_servlet_errorcount_total{module="localhost/pestoapp",servlet="jsp"} 0.0
# HELP tomcat_servlet_processingtime_total Tomcat servlet processingTime total
# TYPE tomcat_servlet_processingtime_total counter
tomcat_servlet_processingtime_total{module="localhost/pestoapp",servlet="FacesServlet"} 0.0
tomcat_servlet_processingtime_total{module="localhost/pestoapp",servlet="com.example.GreetingPagesServlet"} 0.0
tomcat_servlet_processingtime_total{module="localhost/pestoapp",servlet="com.example.GreetingServlet"} 0.0
tomcat_servlet_processingtime_total{module="localhost/pestoapp",servlet="com.example.RestActivator"} 239.0
tomcat_servlet_processingtime_total{module="localhost/pestoapp",servlet="default"} 0.0
tomcat_servlet_processingtime_total{module="localhost/pestoapp",servlet="jsp"} 0.0
# HELP tomcat_servlet_requestcount_total Tomcat servlet requestCount total
# TYPE tomcat_servlet_requestcount_total counter
tomcat_servlet_requestcount_total{module="localhost/pestoapp",servlet="FacesServlet"} 0.0
tomcat_servlet_requestcount_total{module="localhost/pestoapp",servlet="com.example.GreetingPagesServlet"} 0.0
tomcat_servlet_requestcount_total{module="localhost/pestoapp",servlet="com.example.GreetingServlet"} 0.0
tomcat_servlet_requestcount_total{module="localhost/pestoapp",servlet="com.example.RestActivator"} 1.0
tomcat_servlet_requestcount_total{module="localhost/pestoapp",servlet="default"} 0.0
tomcat_servlet_requestcount_total{module="localhost/pestoapp",servlet="jsp"} 0.0
# HELP tomcat_session_expiredsessions_total Tomcat session expiredSessions total
# TYPE tomcat_session_expiredsessions_total counter
tomcat_session_expiredsessions_total{context="/pestoapp",host="localhost"} 0.0
# HELP tomcat_session_processingtime_total Tomcat session processingTime total
# TYPE tomcat_session_processingtime_total counter
tomcat_session_processingtime_total{context="/pestoapp",host="localhost"} 0.0
# HELP tomcat_session_rejectedsessions_total Tomcat session rejectedSessions total
# TYPE tomcat_session_rejectedsessions_total counter
tomcat_session_rejectedsessions_total{context="/pestoapp",host="localhost"} 0.0
# HELP tomcat_session_sessioncounter_total Tomcat session sessionCounter total
# TYPE tomcat_session_sessioncounter_total counter
tomcat_session_sessioncounter_total{context="/pestoapp",host="localhost"} 0.0
# HELP tomcat_threadpool_acceptcount Tomcat threadpool acceptCount
# TYPE tomcat_threadpool_acceptcount gauge
tomcat_threadpool_acceptcount{port="8080",protocol="http-nio"} 100.0
# HELP tomcat_threadpool_connectioncount Tomcat threadpool connectionCount
# TYPE tomcat_threadpool_connectioncount gauge
tomcat_threadpool_connectioncount{port="8080",protocol="http-nio"} 2.0
# HELP tomcat_threadpool_currentthreadcount Tomcat threadpool currentThreadCount
# TYPE tomcat_threadpool_currentthreadcount gauge
tomcat_threadpool_currentthreadcount{port="8080",protocol="http-nio"} 10.0
# HELP tomcat_threadpool_currentthreadsbusy Tomcat threadpool currentThreadsBusy
# TYPE tomcat_threadpool_currentthreadsbusy gauge
tomcat_threadpool_currentthreadsbusy{port="8080",protocol="http-nio"} 0.0
# HELP tomcat_threadpool_keepalivecount Tomcat threadpool keepAliveCount
# TYPE tomcat_threadpool_keepalivecount gauge
tomcat_threadpool_keepalivecount{port="8080",protocol="http-nio"} 1.0
# HELP tomcat_threadpool_maxthreads Tomcat threadpool maxThreads
# TYPE tomcat_threadpool_maxthreads gauge
tomcat_threadpool_maxthreads{port="8080",protocol="http-nio"} 200.0
# HELP tomcat_threadpool_minsparethreads Tomcat threadpool minSpareThreads
# TYPE tomcat_threadpool_minsparethreads gauge
tomcat_threadpool_minsparethreads{port="8080",protocol="http-nio"} 10.0

```



## k6

```Powershell
choco install k6
```

then inside the k6 project:

```bash
pnpm i && pnpm run bundle

k6 run dist/get-200-status-test.js

```
