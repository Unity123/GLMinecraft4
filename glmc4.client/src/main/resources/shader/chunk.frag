#version 410 core

in vec2 uv1;
in vec3 tint1;

uniform sampler2D tex;

out vec4 fc;

void main(){
    fc=vec4(texture(tex, uv1).xyz * tint1, 1);
}