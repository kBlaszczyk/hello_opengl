package de.orchound.rendering


fun main() {
	val window = Window("Hello OpenGL", 800, 600)
	val renderable = Renderable()

	while (!window.shouldClose()) {
		window.prepareFrame()
		renderable.render()
		window.finishFrame()
	}
	window.destroy()
}
