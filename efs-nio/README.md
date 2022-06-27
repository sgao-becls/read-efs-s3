### File name format

**\<filename prefix>.\<file num>**

i.e. nio_test.0, nio_test_1

fio tool can create such files with the specific name.

```shell
fio --ioengine=libaio --iodepth=8 --readwrite=write --numjobs=2 --bs=4k --filesize=1M --name=efs-nio-test --directory=/home/ec2-user/efs  --thread --filename_format='$jobname.$jobnum'
```