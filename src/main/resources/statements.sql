-- http://www.web4j.com/web4j/javadoc/hirondelle/web4j/database/package-summary.html#BasicIdea
BOUQUET_PRICE {
SELECT SUM(price) + b.assemble_price
FROM bouquet b JOIN flower f ON b.id=f.bouquet_id
WHERE b.id=?
}
