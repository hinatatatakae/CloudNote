#!/usr/bin/env sh
host="$1"; shift
port="$1"; shift
until nc -z "$host" "$port"; do
	echo "waiting for $host:$port"
	sleep 2
done
exec "$@"