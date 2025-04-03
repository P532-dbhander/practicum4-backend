@echo off

docker "compose" "down" --volume && docker "compose" "up" "--build"
