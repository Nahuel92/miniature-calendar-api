function fetchData(cmp, random = false) {
    const date = cmp.state.date ?
        `/${(cmp.state.date + '').replaceAll('-', '').substring(2)}`
        : '';
    fetch(`http://localhost:8080/pictures${date}?random=${random}`)
        .then((res) => {
            if (res.ok) {
                return res.json().then(items => {
                    cmp.setState({
                        items,
                        loaded: true,
                    });
                })
            }
            return res.json().then(error => {
                cmp.setState({
                    error,
                    loaded: true,
                });
            })
        });
}
