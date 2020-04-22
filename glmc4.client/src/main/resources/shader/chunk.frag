#version 410 core

in vec2 uv1, uv2;
in vec3 tint1, tint2;

uniform sampler2D tex;

out vec4 fc;

void main(){
    //    fc=vec4(uv, 0, 1);
    //148 grayscale, 93 red, 183 green, 53 blue
    vec3 c1=texture(tex, uv1).xyz*tint1;
    vec3 c2=texture(tex, uv2).xyz*tint2;
    vec3 c=c1;
    if (c2!=vec3(0))c=c2;
    fc=vec4(c, 1);
    //    fc=texture(tex, vec2(uv.x,uv.y));
    //    if(fc.x==fc.y&&fc.y==fc.z)fc*=vec4(142,206,93,256)/256.0;
}