# Beats Music Playlist Exporter

## What and Why
Beats Music is becoming Apple Music on June 30th, 2015 and...well...I don't know much about what's going on with Apple Music, but there probably won't be a REST API so I wrote a simple Clojure app that uses my beats-clj library to pull and download the track listing for any playlists you created on Beats Music.

## Usage

You'll need to get a Beats Music API application created to get an app key and secret and update the core.clj file accordingly.

Once you have those, use [this tool](http://thatmikeflynn.com/beats-clj/resources/) to handle your oauth and get your token. Create the app, download the latest release (jar and bash script) and update the bash script with your app key, secret and token. Now run:

```
./run.sh
```

## Yeah I know...

It's not a polished app and you have to create your own Beats Music API Application. I created this really quickly today to get my family's playlists backed up and then at the last second figured I'd throw it up in a gist.
