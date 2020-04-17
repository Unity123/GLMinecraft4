#version 410 core

in vec2 uv;

out vec4 fc;

void main(){
    fc=vec4(uv, 0, 1);
}