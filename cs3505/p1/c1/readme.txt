handy ffmpeg commands!

# convert into .pcm (not .wav; signed 16-bit big endian)
ffmpeg -i in.mp3 -f s16be out.pcm

# ffplay .pcm (signed 16b b/e)
ffplay -f s16be in.pcm

# convert into .wav with mp2 format
ffmpeg -i in.mp3 -acodec mp2 out.wav

