# fs2-WriterT-memory

This is a repository to reproduce a memory leak using `fs2.Stream` along with an effect type that uses `WriterT`.

Clone this repository, `cd` into the directory, and run the following command to reproduce the issue:

```bash
sbt 'runMain example.TestWriterT'
```

The output will look like this:

```
[info] running (fork) example.TestWriterT
[info] Memory used: 37715968, free: 96499712, total: 134217728, max: 134217728
[info] Memory used: 116555968, free: 17661760, total: 134217728, max: 134217728
[info] Memory used: 131342152, free: 2875576, total: 134217728, max: 134217728
[info] Memory used: 132201208, free: 2016520, total: 134217728, max: 134217728
[error] Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "io-compute-9"
[error] Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "io-scheduler"
```

You can compare this to the same code running with `IO` with the following command:

```bash
sbt 'runMain example.TestIO'
```

The output will look like this (and will continue until you `ctrl-c`):

```
[info] running (fork) example.TestIO
[info] Memory used: 36864000, free: 97353728, total: 134217728, max: 134217728
[info] Memory used: 52888112, free: 81329616, total: 134217728, max: 134217728
[info] Memory used: 21426472, free: 112791256, total: 134217728, max: 134217728
[info] Memory used: 84356656, free: 49861072, total: 134217728, max: 134217728
[info] Memory used: 68621584, free: 65596144, total: 134217728, max: 134217728
[info] Memory used: 69683744, free: 64533984, total: 134217728, max: 134217728
[info] Memory used: 68645936, free: 65571792, total: 134217728, max: 134217728
[info] Memory used: 45577744, free: 88639984, total: 134217728, max: 134217728
[info] Memory used: 49780784, free: 84436944, total: 134217728, max: 134217728
[info] Memory used: 57120032, free: 77097696, total: 134217728, max: 134217728
[info] Memory used: 64985376, free: 69232352, total: 134217728, max: 134217728
...
```
