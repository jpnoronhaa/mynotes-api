package com.jpnoronhaa.mynotes.service;

import com.jpnoronhaa.mynotes.dto.SpellingErrorDTO;
import jakarta.annotation.PostConstruct;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BrazilianPortuguese;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SpellCheckService {

    private final Parser parser = Parser.builder().build();
    private final TextContentRenderer textRenderer = TextContentRenderer.builder().build();
    private final Map<String, JLanguageTool> langTools = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.langTools.put("pt-br", new JLanguageTool(new BrazilianPortuguese()));
        this.langTools.put("en", new JLanguageTool(new AmericanEnglish()));
    }

    public List<SpellingErrorDTO> checkTextSpelling(String text, String langCode) {
        if (text == null || text.isBlank()) return Collections.emptyList();

        try {
            JLanguageTool tool = getTool(langCode);

            List<RuleMatch> matches = tool.check(text);

            return mapMatchesToDTO(matches, text);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing spelling checker", e);
        }
    }

    public List<SpellingErrorDTO> checkMarkdownSpelling(String markdownContent, String langCode) {
        if (markdownContent == null || markdownContent.isBlank()) return Collections.emptyList();

        try {
            JLanguageTool tool = getTool(langCode);

            Node document = parser.parse(markdownContent);
            String plainText = textRenderer.render(document);

            List<RuleMatch> matches = tool.check(plainText);

            return mapMatchesToDTO(matches, plainText);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing spelling checker", e);
        }
    }

    private List<SpellingErrorDTO> mapMatchesToDTO(List<RuleMatch> matches, String contentRef) {
        return matches.stream()
                .map(match -> new SpellingErrorDTO(
                        match.getMessage(),
                        contentRef.substring(match.getFromPos(), match.getToPos()),
                        match.getSuggestedReplacements(),
                        match.getFromPos(),
                        match.getToPos()
                ))
                .toList();
    }

    private JLanguageTool getTool(String langCode) {
        return this.langTools.getOrDefault(langCode != null ? langCode.toLowerCase() : "en", this.langTools.get("en"));
    }
}
