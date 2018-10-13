===========Process/Thread Synchronization============

1. This particular projects demonstrates how "synchronized" keyword works in java for
   maintaining the synchronous access to the Shared Resources


Working:
I- Each individual thread that take exactly one file(.mp3) for playing the song,
   there are total 4 threads as such.
II- Each thread plays its song by accessing play() function via Player object
III- Player object is placed in a method(void play_Song(InputStream muz,String songName)) of 
      Speaker class,so each thread has to pass a common object of Speaker class.
IV - synchronized keyword is used with the method signature to make sure Mutual exclusion
      property holds true for all threads.

Note: Instead of threads, different programs can also be used.

======================================================

Library required: jLayer1.0.1

======================================================
