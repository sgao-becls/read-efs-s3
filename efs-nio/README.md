### File name format

**\<filename prefix>.\<file num>**

i.e. nio_test.0, nio_test_1

fio tool can create such files with the specific name.

```shell
fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=2 --bs=4k --filesize=1M --name=efs-nio-test --directory=/home/ec2-user/efs  --thread --filename_format='$jobname.$jobnum'
```

### Golden standard

```shell
Experiment	    Number of Files	Avg Events/file	Stripe Size	Plot Type	Number of Plots	Plot Time(ms)	 Populations	Comp	Channels
Four 384 well plates (Many files)	1496	6100	48KB	Density Dot	2992	12561	1	False	10
800 channels, 1 file	               1	20000	157KB	Density Dot	2400	18919	4	False	800
Large Amgen (Many tailored pops)	 220	100000	782KB	Density Dot	3960	26205	18	False	1
5M events - 10 files	              10	5223090	40MB	Density Dot	 840	871146	4	False	21

2992/1496/1 = 2  
2400/1/4    = 600
3960/220/18  = 1
840/10/4     = 21


fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=48K --name=four_384_well_plates --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=600 --bs=4k --filesize=157K --name=800_channel_1_file --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=782K --name=large_amgen --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=40MB --name=5m_events --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'


fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=8MB --name=1m_events --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=40MB --name=5m_events --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=80MB --name=10m_events --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=200MB --name=25m_events --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=25 --bs=4k --filesize=320MB --name=40m_events --directory=/home/ec2-user/efs --filename_format='$jobname.$jobnum'

```