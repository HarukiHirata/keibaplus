const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
const pageSize = 20;
async function search(page = 0) {
    const requestBody = {
        raceDateFrom: document.getElementById("raceDateFrom").value,
        raceDateTo: document.getElementById("raceDateTo").value,
        kenshuNo: document.getElementById("kenshuNo").value,
        courseNo: document.getElementById("courseNo").value
    };

    const resultBody = document.getElementById("resultBody");
    const pagination = document.getElementById("pagination");
    const resultCount = document.getElementById("resultCount");

    try {
        const response = await fetch(`/api/shuushisummary/itiran?page=${page}&size=${pageSize}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            throw new Error("検索結果の取得に失敗しました");
        }

        const data = await response.json();

        resultBody.innerHTML = "";
        pagination.innerHTML = "";
        resultCount.textContent = `検索結果：${data.totalElements}件`;

        if (!data.content || data.content.length === 0) {
        resultBody.innerHTML = `
            <tr>
            <td colspan="8" class="text-center">検索結果は0件です</td>
            </tr>
        `;
        return;
        }

        data.content.forEach(row => {
        const tr = document.createElement("tr");
        const raceNo = row.raceNo === 0 ? "" : row.raceNo;
        tr.innerHTML = `
            <td>
            <a href="/shuushiedit/${row.shuushiNo}" class="btn btn-info">編集</a>
            </td>
            <td>
            <a href="/shuushidelete/${row.shuushiNo}" class="btn btn-danger">削除</a>
            </td>
            <td>${row.raceDate ?? ""}</td>
            <td>${row.courseName ?? ""}</td>
            <td>${raceNo ?? ""}</td>
            <td>${row.kenshuName ?? ""}</td>
            <td>${row.kounyuuKingaku ?? ""}</td>
            <td>${row.haraimodoshi ?? ""}</td>
        `;
        resultBody.appendChild(tr);
        });

        renderPagination(data.page, data.totalPages);


    } catch (error) {
        console.error(error);
        pagination.innerHTML = "";
        resultCount.textContent = "";
        resultBody.innerHTML = `
        <tr>
            <td colspan="8" class="text-center text-danger">検索中にエラーが発生しました</td>
        </tr>
        `;
    }
}

function renderPagination(currentPage, totalPages) {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";

    if (totalPages <= 1) {
        return;
    }

    pagination.appendChild(
        createPageItem("前へ", currentPage - 1, currentPage === 0)
    );

    // 現在ページの前後2ページを表示
    const startPage = Math.max(0, currentPage - 2);
    const endPage = Math.min(totalPages - 1, currentPage + 2);

    for (let page = startPage; page <= endPage; page++) {
        pagination.appendChild(
            createPageItem(
                String(page + 1),
                page,
                false,
                page === currentPage
            )
        );
    }

    pagination.appendChild(
        createPageItem(
            "次へ",
            currentPage + 1,
            currentPage >= totalPages - 1
        )
    );
}

function createPageItem(label, page, disabled, active = false) {
    const li = document.createElement("li");
    li.className = "page-item";

    if (disabled) {
        li.classList.add("disabled");
    }

    if (active) {
        li.classList.add("active");
    }

    const button = document.createElement("button");
    button.type = "button";
    button.className = "page-link";
    button.textContent = label;
    button.disabled = disabled;

    if (!disabled && !active) {
        button.addEventListener("click", () => search(page));
    }

    li.appendChild(button);
    return li;
}

window.addEventListener("load", () => search(0));
