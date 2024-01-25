<template>
    <div v-if="editor">
        <button
            @click="editor.chain().focus().toggleBold().run()"
            :disabled="!editor.can().chain().focus().toggleBold().run()"
            :class="{'is-active' : editor.isActive('bold')}">
            <v-icon icon="mdi-format-bold"></v-icon>
        </button>
        <button
            @click="editor.chain().focus().toggleItalic().run()"
            :disabled="!editor.can().chain().focus().toggleItalic().run()"
            :class="{'is-active' : editor.isActive('italic')}">
            <v-icon icon="mdi-format-italic"></v-icon>
        </button>
        <button
            @click="editor.chain().focus().toggleStrike().run()"
            :disabled="!editor.can().chain().focus().toggleStrike().run()"
            :class="{'is-active' : editor.isActive('strike')}">
            <v-icon icon="mdi-format-strikethrough"></v-icon>
        </button>
        <button
            @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
            <v-icon icon="mdi-format-header-1"></v-icon>
        </button>
        <button
            @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
            :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
            <v-icon icon="mdi-format-header-2"></v-icon>
        </button>
        <button
        @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
        :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
            <v-icon icon="mdi-format-header-3"></v-icon>
        </button>
        <button
            @click="editor.chain().focus().undo().run()"
            :disabled="!editor.can().chain().focus().undo().run()">
            <v-icon icon="mdi-undo"></v-icon>
        </button>
        <button
            @click="editor.chain().focus().redo().run()"
            :disabled="!editor.can().chain().focus().redo().run()">
            <v-icon icon="mdi-redo"></v-icon>
        </button>
    </div>
    <EditorContent :editor="editor" style="height:90%"/>

</template>

<script setup>
import {ref, watch, onMounted, onBeforeMount} from 'vue';
import StarterKit from "@tiptap/starter-kit"
import {Editor, EditorContent} from "@tiptap/vue-3";
import Image from "@tiptap/extension-image";

const props = defineProps({
    modelValue: {
        type:String,
        default:'',
    }
});

const emit = defineEmits(['update:modelValue'])

const editor = ref(null);

// modelValue가 바뀔 때마다 실행되는 watcher
watch(() => props.modelValue, (newValue) => {
    if (!editor.value) {
        return;
    }
    const isSame = editor.value.getHTML() === newValue;

    if (isSame) {
        return;
    }

    editor.value.commands.setContent(newValue,false);
});

onMounted(() => {
    editor.value = new Editor({
        extensions: [StarterKit, Image],
        content: props.modelValue,
        onUpdate: ({editor}) => {
            emit('update:modelValue', editor.getHTML());
        }
    });
});

onBeforeMount(() => {
    if(editor.value) {
        editor.value.destroy();
        editor.value = null;
    }
})

</script>

<style lang="scss">
.ProseMirror {
  height: 300px;
  border: 1px solid black;
  border-radius: 10px;
  padding: 6px;
  overflow: scroll;  // 내용 넘치면 스크롤바

  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0d0d0d;
    color: #fff;
    font-family: "JetBrainsMono", monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0d0d0d, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0d0d0d, 0.1);
    margin: 2rem 0;
  }
}

</style>