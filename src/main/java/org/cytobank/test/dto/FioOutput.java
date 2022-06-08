package org.cytobank.test.dto;

/**
 * io=执⾏了多少M的IO
 * bw=平均IO带宽
 * iops=IOPS
 * runt=线程运⾏时间
 * slat=提交延迟
 * clat=完成延迟
 * lat=响应时间
 * bw=带宽
 * cpu=利⽤率
 * IO depths=io队列
 * IO submit=单个IO提交要提交的IO数
 * IO complete=Like the above submit number, but for completions instead.
 * IO issued=The number of read/write requests issued, and how many of them were short.
 * IO latencies=IO完延迟的分布
 * io=总共执⾏了多少size的IO
 * aggrb=group总带宽
 * minb=最⼩.平均带宽.
 * maxb=最⼤平均带宽.
 * mint=group中线程的最短运⾏时间.
 * maxt=group中线程的最长运⾏时间.
 * ios=所有group总共执⾏的IO数.
 * merge=总共发⽣的IO合并数.
 * ticks=Number of ticks we kept the disk busy.
 * io\_queue=花费在队列上的总共时间.
 * util=磁盘利⽤率
 */
public class FioOutput {
}
