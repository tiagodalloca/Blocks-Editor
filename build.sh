#!/bin/bash

echo "Cleaning ..."
lein clean
echo -e "${GREEN}Done${NC}"
# echo "Compiling cljs ..."
lein cljsbuild once release
echo -e "${GREEN}Done${NC}"
echo "Packaging ..."
npm run build
echo -e "${GREEN}Done${NC}"
