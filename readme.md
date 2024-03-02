# SaveFix

Fixes issues with auto-saves causing lag spikes.


**Without the mod**, 13 players online, i5-10500T, 8GB RAM, dual NVMe SSD, RAID 1, `sync-chunk-writes=false`
![](https://github.com/henkelmax/save-fix/assets/13237524/c9626e83-8229-4948-9771-62faf150bf47)

**With the mod**, 13 players online, i5-10500T, 8GB RAM, dual NVMe SSD, RAID 1, `sync-chunk-writes=false`
![](https://github.com/henkelmax/save-fix/assets/13237524/8c021bb2-c183-4e66-b249-44f34e32be9b)


## What does it do?

This mod changes the NBT files in the worlds `data` folder to be saved without compression.
It also uses buffered IO for storing NBT data.


## Additional Improvements

If your server is running on Linux, make sure to set `sync-chunk-writes` to `false` in the `server.properties`.
