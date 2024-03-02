# SaveFix

Fixes issues with auto-saves causing lag spikes.

## What does it do?

This mod changes the NBT files in the worlds `data` folder to be saved without compression.
It also uses buffered IO for storing NBT data.

## Additional Improvements

If your server is running on Linux, make sure to set `sync-chunk-writes` to `false` in the `server.properties`.
