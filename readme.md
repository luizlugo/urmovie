# Urmovie

Urmovie is an android application that let you see what are the popular or top rated movies of the film industry. Data is being pulled from https://www.themoviedb.org/

### Installation

Urmovie requires [Android studio](https://developer.android.com/studio) v3.5+ to run.

Clone this repository and import into Android studio

```sh
$ git clone https://github.com/luizlugo/urmovie.git
```

### Libraries

Urmovie is currently extended with the following libraries. Instructions on how to use them in your own application are linked below.

| Library | README |
| ------ | ------ |
| Picasso | https://github.com/square/picasso |
| Retrofit | https://square.github.io/retrofit/ |
| RxJava | https://github.com/ReactiveX/RxJava |
| Butterknife | https://github.com/JakeWharton/butterknife |
| Android Material Design | https://material.io/develop/android/ |
| Timber | https://github.com/JakeWharton/timber |

### Configuration
You have to request an api key from themoviedb.org website following this instructions: https://developers.themoviedb.org/3/getting-started/introduction.

Once you have your key you have to add it to the ``Constants.java`` file:

```sh
moviedbApiKey = "[ADD_YOUR_API_KEY_HERE]"
```

### Todos

 - Write unit tests
 - Add persisten storage

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
