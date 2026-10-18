// Named explicitly because the name derived from the jar file ("java.primitives") would land in
// the java.* namespace the JDK reserves for platform modules.
module com.github.zrdj.java.primitives {
    exports com.github.zrdj.java.primitives.bytes;
    exports com.github.zrdj.java.primitives.bytes.encrypted;
    exports com.github.zrdj.java.primitives.bytes.hash;
    exports com.github.zrdj.java.primitives.bytes.key;
    exports com.github.zrdj.java.primitives.characters;
    exports com.github.zrdj.java.primitives.codec;
    exports com.github.zrdj.java.primitives.codec.base64;
    exports com.github.zrdj.java.primitives.codec.hex;
    exports com.github.zrdj.java.primitives.exception;
    exports com.github.zrdj.java.primitives.hashing;
    exports com.github.zrdj.java.primitives.numbers;
    exports com.github.zrdj.java.primitives.text;
}
