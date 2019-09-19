package de.orchound.rendering

import org.lwjgl.opengl.GL20.*

class SimpleShader {

	private val handle: Int

	init {
		val vertexShaderSource = """
			|#version 330
			|layout(location = 0) in vec3 in_position;
			|void main(void) {
			|	gl_Position = vec4(in_position, 1);
			|}
		""".trimMargin()

		val fragmentShaderSource = """
			|#version 330
			|out vec3 out_colour;
			|void main(void) {
			|	out_colour = vec3(1.0, 1.0, 0.0);
			|}
		""".trimMargin()

		handle = createShaderProgram(vertexShaderSource, fragmentShaderSource)
	}

	fun bind() = glUseProgram(handle)
	fun unbind() = glUseProgram(0)

	private fun createShaderProgram(vertexShaderSource: String, fragmentShaderSource: String): Int {
		val vertexShader = compileShader(vertexShaderSource, GL_VERTEX_SHADER)
		val fragmentShader = compileShader(fragmentShaderSource, GL_FRAGMENT_SHADER)

		val programId = glCreateProgram()
		glAttachShader(programId, vertexShader)
		glAttachShader(programId, fragmentShader)

		glBindAttribLocation(programId, 0, "in_position")

		glLinkProgram(programId)

		validateShaderLinking(programId)
		validateShaderProgram(programId)

		return programId
	}

	private fun compileShader(shaderSource: String, type: Int): Int {
		val shaderId = glCreateShader(type)

		glShaderSource(shaderId, shaderSource)
		glCompileShader(shaderId)

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
			val info = getShaderInfoLog(shaderId)
			val shaderType = if (type == GL_VERTEX_SHADER) "Vertex" else "Fragment"
			throw Exception("$shaderType shader compilation failed: $info")
		}

		return shaderId
	}

	private fun getProgramInfoLog(programId:Int):String {
		return glGetProgramInfoLog(programId, GL_INFO_LOG_LENGTH)
	}

	private fun getShaderInfoLog(shaderId:Int):String {
		return glGetShaderInfoLog(shaderId, GL_INFO_LOG_LENGTH)
	}

	private fun validateShaderProgram(programId:Int) {
		glValidateProgram(programId)

		val error = glGetError()
		if (error != 0)
			throw Exception("OpenGL shader creation failed")
	}

	private fun validateShaderLinking(programId: Int) {
		if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
			val info = getProgramInfoLog(programId)
			throw Exception("OpenGL shader linking failed: $info")
		}
	}
}
