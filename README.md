### About
- Despite it's ugly interface, I assure you, it's still not worth using
- There's plenty of obtuse, tiny bugs that don't break anything, just make it less usable, stop being obtuse
- Other than those points, on the plus side, it's completely free and new features are hard to add because the code makes no sense
- If that doesn't make you love it, it's also written in Java, notoriously not-as-slow-as-it-used-to-be, and I still use Swing which is deprecated. I also use Java2D which I'm pretty sure is older than me

### Version 0.0 Build 3 Release Notes

- Mouse position now done with jinput
- Input event handling done after polling, as opposed to in a separate thread
- Decal properties panel with rotation and rotation speed

### Dependencies
- [JSON.Simple](https://github.com/fangyidong/json-simpl)
	- Provides simple tools for reading and writing JSON files
- [GSon](https://github.com/google/gson)
	- JSON.Simple doesn't format the JSON when writing out to look "pretty", GSon is used since it does have that feature
- [LWJGL](https://www.lwjgl.org/download) 
	- the entire LWJGL library is needed, it can be a pain to setup, there are great guides on google like this one: http://thecodinguniverse.com/lwjgl-workspace/
- [RSyntaxTextArea](https://github.com/bobbylight/RSyntaxTextArea)
	- This is really cool, it's a text area which handles syntax highlighting completely for you.
- [RSyntaxTextArea - AutoComplete](https://github.com/bobbylight/AutoComplete)
	- Another really cool tool which attaches to RSyntaxTextArea which allows for auto-completion
- [Jinput] (https://github.com/jinput/jinput)
	- Lower level input polling library for java, used instead of Java's in-built listener interfaces which have proven to be problematic
	
## Screenshot of Current Progress (13/10/2017)

![Oops the image broke](https://i.imgur.com/SNLysy8.jpg "Elara Editor")
![Oops the image broke](https://i.imgur.com/ZLYNP1j.jpg "Texture blending")