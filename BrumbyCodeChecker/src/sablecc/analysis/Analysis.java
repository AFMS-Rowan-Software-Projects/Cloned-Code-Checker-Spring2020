package sablecc.analysis;

import sablecc.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseTTraditionalComment(TTraditionalComment node);
    void caseTDocumentationComment(TDocumentationComment node);
    void caseTEndOfLineComment(TEndOfLineComment node);
    void caseTLBrace(TLBrace node);
    void caseTRBrace(TRBrace node);
    void caseTLParen(TLParen node);
    void caseTRParen(TRParen node);
    void caseTDataType(TDataType node);
    void caseTNumericConstant(TNumericConstant node);
    void caseTTextLiteral(TTextLiteral node);
    void caseTKeyword(TKeyword node);
    void caseTBlank(TBlank node);
    void caseTIdentifier(TIdentifier node);
    void caseTUnknown(TUnknown node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}