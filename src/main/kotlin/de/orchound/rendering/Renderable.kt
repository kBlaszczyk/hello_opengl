package de.orchound.rendering

import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays


class Renderable {
	private val shader = SimpleShader()
	private val vao = glGenVertexArrays()

	init {
		val vertices = floatArrayOf(-0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0f, 0.5f, 0f)

		val vbo = glGenBuffers()
		glBindBuffer(GL_ARRAY_BUFFER, vbo)
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)
		glBindBuffer(GL_ARRAY_BUFFER, 0)

		glBindVertexArray(vao)
		glEnableVertexAttribArray(0)
		glBindBuffer(GL_ARRAY_BUFFER, vbo)
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
		glBindVertexArray(0)
	}

	fun render() {
		shader.bind()
		glBindVertexArray(vao)
		glDrawArrays(GL_TRIANGLES, 0, 3)
		glBindVertexArray(0)
		shader.unbind()
	}
}
